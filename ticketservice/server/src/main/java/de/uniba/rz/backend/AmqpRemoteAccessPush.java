package de.uniba.rz.backend;

import com.rabbitmq.client.*;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.models.ConnectionManager;
import de.uniba.rz.models.Deserializer;
import de.uniba.rz.models.Serializer;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class AmqpRemoteAccessPush extends Thread  implements RemoteAccess {

    private final String HOST_NAME;
    private final String TICKET_QUEUE_NAME;
    private final String UPDATE_QUEUE_NAME;
    private final String LIST_REQUEST_QUEUE_NAME;
    private final String FANOUT_EXCHANGE_NAME;
    Channel channel;
    BlockingQueue<Ticket> blockingTicketQueue;
    Connection connection;
    TicketStore serverTicketStore;

    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();

    public AmqpRemoteAccessPush(String hostname, String ticketQueueName, String updateQueueName,
                                String listRequestQueueName, String fanoutExchangeName) {
        this.HOST_NAME = hostname;
        this.TICKET_QUEUE_NAME = ticketQueueName;
        this.UPDATE_QUEUE_NAME = updateQueueName;
        this.LIST_REQUEST_QUEUE_NAME = listRequestQueueName;
        this.FANOUT_EXCHANGE_NAME = fanoutExchangeName;
    }

    public void publishMessage() throws IOException {
        byte[] ticketListData = serializer.serializeList(serverTicketStore.getAllTickets());
        channel.basicPublish(FANOUT_EXCHANGE_NAME, "", null, ticketListData);
    }

    @Override
    public void prepareStartup(TicketStore ticketStore) throws IOException, TimeoutException {
        this.serverTicketStore = ticketStore;
        blockingTicketQueue = new ArrayBlockingQueue<Ticket>(1, true);
        System.out.println("\t[AMQP-SERVER]: Start waiting for messages");
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(this.HOST_NAME);
            connection = factory.newConnection();
            channel = connection.createChannel();

            // Declare queues for receiving
            channel.queueDeclare(TICKET_QUEUE_NAME, true, false, false, null);
            channel.queueDeclare(UPDATE_QUEUE_NAME, true, false, false, null);
            channel.queueDeclare(LIST_REQUEST_QUEUE_NAME, true, false, false, null);

            // Declare exchange for sending changes in the ticket store to all clients
            channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout", true);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
    public void shutdown() {
            try {
                channel.close();
                connection.close();
            } catch (TimeoutException | IOException e){
                e.printStackTrace();
            }
            this.interrupt();
        System.out.println("\t[AMQP SERVER]: Stopping to listen for messages.");
    }

    @Override
    public void run() {
        // TODO: think of an appropriate strategy to handle the acknowledgement
        try {
            // Ensure that we will only get one message at a time from RabbitMQ
            channel.basicQos(1);
            channel.basicConsume(this.TICKET_QUEUE_NAME, false, "myConsumerTag", new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    try {
                        Ticket ticket = deserializer.deserialize(body);
                        // After receiving, add the message to a thread-safe data structure, like a BlockingQueue
                        blockingTicketQueue.put(ticket);
                        channel.basicPublish("", properties.getReplyTo(), null,
                                "Ticket succsessfully delivered.".getBytes());

                        // Acknowledge explicitly
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (InterruptedException e) {
                        // Preserve the interrupt for the caller (channel's thread pool)
                        Thread.currentThread().interrupt();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Ticket ticket = blockingTicketQueue.take();
                System.out.println("\t[AMQP-SERVER]: >Received: " + ticket.toText());

                // Store new ticket in TicketStore
                serverTicketStore.storeNewTicket(ticket.getReporter(), ticket.getTopic(), ticket.getDescription(),
                        ticket.getType(), ticket.getPriority());

                // Broadcast message to all subscribed clients
                publishMessage();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
