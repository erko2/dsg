package de.uniba.rz.app;

import com.rabbitmq.client.*;
import de.uniba.rz.entities.*;
import de.uniba.rz.models.Deserializer;
import de.uniba.rz.models.Serializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AmqpTicketManagementBackend implements TicketManagementBackend {
    private HashMap<Integer, Ticket> amqpTicketStore = new HashMap<>();
    private AtomicInteger nextId;

    private final String HOSTNAME;
    private final String NEW_TICKET_QUEUE_NAME;
//    private final String UPDATE_QUEUE_NAME;
//    private final String GET_ALL_TICKETS_QUEUE_NAME;
    private final String FANOUT_EXCHANGE_NAME;
    private String ticketListQueueName;
    private String ackQueueName;
    private Serializer serializer = new Serializer();
    private Deserializer deserializer = new Deserializer();
    private Connection connection;
    private Channel channel;

    public AmqpTicketManagementBackend(String hostname, String newTicketQueueName,
                                       String updateQueueName, String getAllTicketsQueueName, String fanoutName) {
        this.HOSTNAME = hostname;
        this.NEW_TICKET_QUEUE_NAME = newTicketQueueName;
//        this.UPDATE_QUEUE_NAME = updateQueueName;
//        this.GET_ALL_TICKETS_QUEUE_NAME = getAllTicketsQueueName;
        this.FANOUT_EXCHANGE_NAME = fanoutName;
        nextId = new AtomicInteger(1);
    }

    public void startUp() {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(this.HOSTNAME);
            connection = factory.newConnection();
            channel = connection.createChannel();

            // Fanout subscribtion for getting the most recent ticket store from the server
            channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout", true);
            ticketListQueueName = channel.queueDeclare().getQueue();
            channel.queueBind(ticketListQueueName, FANOUT_EXCHANGE_NAME, "");

            // Initialize and subscribe Queue for receiving acks/responses from server
            ackQueueName = channel.queueDeclare().getQueue();
            channel.basicConsume(ackQueueName, true, ((consumerTag, message) -> {
                String ack = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println("\t [CLIENT]: >AckQueue Received: " + ack);
            }), consumerTag -> {
                System.out.println(consumerTag);
            });

            // Declare queues for sending to server
            channel.queueDeclare(NEW_TICKET_QUEUE_NAME, true, false, false, null);
//            channel.queueDeclare(UPDATE_QUEUE_NAME, true, false, false, null);
//            channel.queueDeclare(GET_ALL_TICKETS_QUEUE_NAME, true, false, false, null);

            subscribeGetAllTicketsQueue();

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void subscribeGetAllTicketsQueue() throws IOException, TimeoutException {
        channel.basicConsume(ticketListQueueName, true, ((consumerTag, message) -> {
            List<Ticket> serverTicketList = deserializer.deserializeList(message.getBody());
            for (Ticket ticket : serverTicketList) {
                amqpTicketStore.put(ticket.getId(), ticket);
            }
            System.out.println("\t [CLIENT]: >Server Ticket List Received: " + serverTicketList);
        }), consumerTag -> { });
    }

    @Override
    public void triggerShutdown() {
        // local implementation is in memory only - no need to close connections
        // and free resources

        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e){
            e.printStackTrace();
        }


    }

    @Override
    public Ticket createNewTicket(String reporter, String topic, String description, Type type, Priority priority) {
        Ticket newTicket = new Ticket(nextId.getAndIncrement(), reporter, topic, description, type, priority);

        try {
            byte[] ticketData = serializer.serialize(newTicket);
            // deliveryMode(2) for persistent messages
            channel.basicPublish("", NEW_TICKET_QUEUE_NAME, new AMQP.BasicProperties.Builder().deliveryMode(2)
                    .replyTo(ackQueueName).build(), ticketData);
        } catch (IOException e) {
            // TODO: Think of an appropriate exception handling strategy (e.g., retrying, logging,...)
            e.printStackTrace();
        }

        return (Ticket) newTicket.clone();
    }


    @Override
    public List<Ticket> getAllTickets() {
        return amqpTicketStore.entrySet().stream().map(entry -> (Ticket) entry.getValue().clone())
                .collect(Collectors.toList());
    }

    @Override
    public Ticket getTicketById(int id) throws TicketException {
        if (!amqpTicketStore.containsKey(id)) {
            throw new TicketException("Ticket ID is unknown");
        }

        return (Ticket) getTicketByIdInteral(id).clone();
    }

    private Ticket getTicketByIdInteral(int id) throws TicketException {
        if (!amqpTicketStore.containsKey(id)) {
            throw new TicketException("Ticket ID is unknown");
        }

        return amqpTicketStore.get(id);
    }

    @Override
    public Ticket acceptTicket(int id) throws TicketException {
        System.out.println("\nSending ticket modification: ACCEPTED");

        Ticket ticketToModify = getTicketByIdInteral(id);
        if (ticketToModify.getStatus() != Status.NEW) {
            throw new TicketException(
                    "Can not accept Ticket as it is currently in status " + ticketToModify.getStatus());
        }

        ticketToModify.setStatus(Status.ACCEPTED);
        amqpTicketStore.replace(id, ticketToModify);

        // TODO send Ticket
        return (Ticket) ticketToModify.clone();
    }

    @Override
    public Ticket rejectTicket(int id) throws TicketException {
        System.out.println("\nSending ticket modification: REJECTED");

        Ticket ticketToModify = getTicketByIdInteral(id);
        if (ticketToModify.getStatus() != Status.NEW) {
            throw new TicketException(
                    "Can not reject Ticket as it is currently in status " + ticketToModify.getStatus());
        }

        ticketToModify.setStatus(Status.REJECTED);
        amqpTicketStore.replace(id, ticketToModify);

        // TODO send Ticket
        return (Ticket) ticketToModify.clone();
    }

    @Override
    public Ticket closeTicket(int id) throws TicketException {
        System.out.println("\nSending ticket modification: CLOSED");

        Ticket ticketToModify = getTicketByIdInteral(id);
        if (ticketToModify.getStatus() != Status.ACCEPTED) {
            throw new TicketException(
                    "Can not close Ticket as it is currently in status " + ticketToModify.getStatus());
        }

        ticketToModify.setStatus(Status.CLOSED);
        amqpTicketStore.replace(id, ticketToModify);

        // TODO send Ticket
        return (Ticket) ticketToModify.clone();
    }

    /**
     * Returns the list of all tickets that contain given name(topic); or all when topic is empty.
     * @param name The topic of the ticket to be searched (Ticket has no "name" field)
     * @return List of tickets with given topic
     */
    @Override
    public List<Ticket> getTicketsByName(String name, int offset, int limit) {
        String nameNoSpace = name.stripLeading().stripTrailing();

        if (nameNoSpace.equals(""))    // return all tickets in the case of an empty search string(topic)
            return new ArrayList<>(amqpTicketStore.values());
        else {
            List<Ticket> searchResults = new ArrayList<>();

            // add tickets whose topic does not contain given string
            for (Ticket ticket : amqpTicketStore.values()) {
                if (ticket.getTopic().contains(nameNoSpace))
                    searchResults.add(ticket);
            }

            return searchResults;
        }
    }

    /**
     * Returns the list of all tickets that contain given name(topic) and type.
     * Calls getTicketsByName first, and then filters by Type.
     * @param name The name of the ticket to be searched
     * @param type The {@link Type} of the ticket to be searched
     * @return List of tickets with given topic and type
     */
    @Override
    public List<Ticket> getTicketsByNameAndType(String name, Type type, int offset, int limit) {
        List<Ticket> ticketsByName = getTicketsByName(name, offset, limit);
        List<Ticket> searchResults = new ArrayList<>();

        for (Ticket ticket : ticketsByName) {
            if (ticket.getType().equals(type))
                searchResults.add(ticket);
        }

        return searchResults;
    }
}
