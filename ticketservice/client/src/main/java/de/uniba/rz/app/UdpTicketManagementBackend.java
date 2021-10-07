package de.uniba.rz.app;

import de.uniba.rz.entities.*;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UdpTicketManagementBackend implements TicketManagementBackend {
    HashMap<Integer, Ticket> udpTicketStore = new HashMap<>();

    AtomicInteger nextId;
    String serverHostName;
    int serverPort;
    int clientPort = 4444;
    DatagramSocket clientSocket = null;
    // max udp packet size is 65'507 bytes, see https://en.wikipedia.org/wiki/User_Datagram_Protocol#UDP_datagram_structure
    static final int maxUdpPacketSize = 65500;

    public UdpTicketManagementBackend(String host, int serverPort) {
        this.serverHostName = host;
        this.serverPort = serverPort;
        nextId = new AtomicInteger(1);
    }

    @Override
    public void triggerShutdown() {
        // local implementation is in memory only - no need to close connections
        // and free resources

    }

    @Override
    public Ticket createNewTicket(String reporter, String topic, String description, Type type, Priority priority) {
        System.out.println("\nSending new ticket");

        // Ids are unique and must therefore be maintained by a single "authority" - the server,
        // therefore we create only a temporary ticket, the one to store is converted from the server response
        Ticket serverTicket = sendTicket(new Ticket(nextId.getAndIncrement(), reporter, topic, description, type, priority)); // ticket status set by the constructor
        if (serverTicket != null) {
            udpTicketStore.put(serverTicket.getId(), serverTicket);
            System.out.println("SERVER: " + serverTicket);
            return (Ticket) serverTicket.clone();
        } else return null;
    }


    @Override
    public List<Ticket> getAllTickets() throws TicketException {
        System.out.println("\nRequesting all tickets");
        List<Ticket> serverTicketList = null;
        createClientSocket();
        try {
            SocketAddress serverAddress = new InetSocketAddress(serverHostName, serverPort);
            // the udp-handler differentiates between a create-new-ticket-, a modify-ticket-status-, or a get-all-tickets-request
            // by deserializing the request message into an object and checking its class
            // an object not of type Ticket means a request for all tickets:
            Object obj = (Object) 0;
            // try with resources für die outputstream etc verwenden
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(obj);
            // best practice: os.flush aufrufen, um sicherzustellen, dass wirklich alles rausgeschrieben wird
            byte[] requestData = outputStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(requestData, requestData.length, serverAddress);
            // send packet
            clientSocket.send(packet);

            // wait for and handle server response
            byte[] buffer = new byte[maxUdpPacketSize];
            DatagramPacket responsePacket = new DatagramPacket(buffer, maxUdpPacketSize);
            clientSocket.receive(responsePacket);
            byte[] responseData = responsePacket.getData();
            ByteArrayInputStream in = new ByteArrayInputStream(responseData);
            ObjectInputStream is = new ObjectInputStream(in);
            serverTicketList = (ArrayList<Ticket>) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null)
                clientSocket.close();
            System.out.println(serverTicketList.size() + " Tickets received");
            for (Ticket ticket : serverTicketList) {
                udpTicketStore.put(ticket.getId(), ticket);
            }
            return serverTicketList;
            //return udpTicketStore.entrySet().stream().map(entry -> (Ticket) entry.getValue().clone()).collect(Collectors.toList());
        }
    }

    @Override
    public Ticket getTicketById(int id) throws TicketException {
        if (!udpTicketStore.containsKey(id)) {
            throw new TicketException("Ticket ID is unknown");
        }

        return (Ticket) getTicketByIdInteral(id).clone();
    }

    private Ticket getTicketByIdInteral(int id) throws TicketException {
        if (!udpTicketStore.containsKey(id)) {
            throw new TicketException("Ticket ID is unknown");
        }

        return udpTicketStore.get(id);
    }

    @Override
    public Ticket acceptTicket(int id) throws TicketException {
        System.out.println("\nSending ticket " + id + " modification: ACCEPTED");

        Ticket ticketToModify = getTicketByIdInteral(id);
        ticketToModify.setStatus(Status.ACCEPTED);
        Ticket serverTicket = sendTicket(ticketToModify);

        udpTicketStore.replace(id, serverTicket);

        if (serverTicket.getStatus() != Status.ACCEPTED)
            // put a warning dialog instead of "throw new TicketException" b/c the later one prevents the interface from updating
            JOptionPane.showMessageDialog(null, "Ticket " + serverTicket.getId() + " already modified to "
                    + serverTicket.getStatus() + " by another client.\nStatus will be refreshed.", "Ticket already modified", 2);
        else System.out.println("Ticket " + serverTicket.getId() + " successfully set to ACCEPTED");

        return (Ticket) serverTicket.clone();
    }

    @Override
    public Ticket rejectTicket(int id) throws TicketException {
        System.out.println("\nSending ticket modification: REJECTED");
        Ticket ticketToModify = getTicketByIdInteral(id);
        ticketToModify.setStatus(Status.REJECTED);
        Ticket serverTicket = sendTicket(ticketToModify);

        udpTicketStore.replace(id, serverTicket);

        if (serverTicket.getStatus() != Status.REJECTED)
            // put a warning dialog instead of "throw new TicketException" b/c the later one prevents the interface from updating
            JOptionPane.showMessageDialog(null, "Ticket " + serverTicket.getId() + " already modified to "
                    + serverTicket.getStatus() + " by another client.\nStatus will be refreshed.", "Ticket already modified", 2);
        else System.out.println("Ticket " + serverTicket.getId() + " successfully set to REJECTED");

        return (Ticket) serverTicket.clone();
    }

    @Override
    public Ticket closeTicket(int id) throws TicketException {
        System.out.println("\nSending ticket modification: CLOSED");

        Ticket ticketToModify = getTicketByIdInteral(id);
        ticketToModify.setStatus(Status.CLOSED);
        Ticket serverTicket = sendTicket(ticketToModify);

        udpTicketStore.replace(id, serverTicket);

        if (serverTicket.getStatus() != Status.CLOSED)
            // put a warning dialog instead of "throw new TicketException" b/c the later one prevents the interface from updating
            JOptionPane.showMessageDialog(null, "Ticket " + serverTicket.getId() + " already modified to "
                    + serverTicket.getStatus() + " by another client.\nStatus will be refreshed.", "Ticket already modified", 2);
        else System.out.println("Ticket " + serverTicket.getId() + " successfully set to CLOSED");

        return (Ticket) serverTicket.clone();
    }

    /**
     * Sends a request for the creation/modification of a ticket
     *
     * @param ticket
     * @return A copy of the server-ticket with its new state - server-generated Id / new status / old status if modification was not possible
     */
    private Ticket sendTicket(Ticket ticket) {
        // socket sollte einmal aufgemacht werden und nicht bei jedem create Ticket neu
        createClientSocket();
        Ticket serverTicket = null;
        try {
            // create address of recepient
            SocketAddress serverAddress = new InetSocketAddress(serverHostName, serverPort);
            // create packet with data of serialized ticket
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(ticket);
            byte[] requestData = outputStream.toByteArray();
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, serverAddress);

            clientSocket.send(requestPacket);

            // wait for server response and get its data
            byte[] buffer = new byte[maxUdpPacketSize];
            DatagramPacket responsePacket = new DatagramPacket(buffer, maxUdpPacketSize);
            clientSocket.receive(responsePacket);
            byte[] responseData = responsePacket.getData();

            // deserialize into Ticket
            ByteArrayInputStream in = new ByteArrayInputStream(responseData);
            ObjectInputStream is = new ObjectInputStream(in);
            serverTicket = (Ticket) is.readObject();
            // TODO: better exception handling
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null)
                clientSocket.close();
            return serverTicket;
        }
    }

    /*
     * Calls itself until it can bind the DatagramSocket to a free port.
     *
     * This code was written for the case of multiple udp-clients from the same machine,
     * still it is unnecessary, since it is impossible that a person can change between client windows so quickly,
     * i.e. a udp-request will be finished and therefore our port 4444 freed, before the user can change to the next client.
     *
     * Leaving it here for now.
     */
    private void createClientSocket() {
        try {
            clientSocket = new DatagramSocket(clientPort);
        } catch (SocketException e) {
            clientPort++;
            createClientSocket();
        }
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
            return new ArrayList<>(udpTicketStore.values());
        else {
            List<Ticket> searchResults = new ArrayList<>();

            // add tickets whose topic does not contain given string
            for (Ticket ticket : udpTicketStore.values()) {
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
