package de.uniba.rz.app;

import de.uniba.rz.entities.*;

import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RestTicketManagementBackend implements TicketManagementBackend {
    private String serverURI;
    private AtomicInteger nextId;
    private HashMap<Integer, Ticket> restTicketStore = new HashMap<>();
    private Client client;    // only for closing later, everywhere else we use WebTarget
    private WebTarget webTarget;    // to save the target location

    public RestTicketManagementBackend(String serverUrl, String serverPort) {
        nextId = new AtomicInteger(1);
        serverURI = "http://" + serverUrl + ":" + serverPort;
        client = ClientBuilder.newClient();
        webTarget = client.target(serverURI);
    }

    @Override
    public Ticket createNewTicket(String reporter, String topic, String description, Type type, Priority priority)
            throws TicketException {
        // Ids are unique and must therefore be maintained by a single "authority" - the server,
        // therefore we create only a temporary ticket, the one to store is converted from the server response
        Ticket ticket = new Ticket(nextId.getAndIncrement(), reporter, topic, description, type, priority);
        Entity<Ticket> entity = Entity.json(ticket);

        Response response = webTarget
                .path("/tickets")
                .request(MediaType.APPLICATION_XML)
                .header("Content-Type", "application/json") // in POSTs/PUTs, content-type tells the server what is sent
                .post(entity);

        // TODO: check possible status codes and process the response
        System.out.println("Status: " + response.getStatus() +
                "\nLocation: " + response.getLocation());

        Ticket newTicket = response.readEntity(Ticket.class);
        restTicketStore.put(newTicket.getId(), newTicket);

        return newTicket;
    }

    @Override
    public List<Ticket> getAllTickets() throws TicketException {
        final Response response = webTarget
                .path("/tickets")
                .request(MediaType.APPLICATION_XML)
                .get();

        if (response.getStatus() != 200)
            return new ArrayList<Ticket>();

        List<Ticket> ticketList = response.readEntity(new GenericType<List<Ticket>>() {});
        for (Ticket ticket : ticketList) {
            restTicketStore.put(ticket.getId(), ticket);
        }

        return ticketList;
    }


    @Override
    public Ticket getTicketById(int id) throws TicketException {
        if (!restTicketStore.containsKey(id)) {
            throw new TicketException("Ticket ID is unknown");
        }

        return (Ticket) getTicketByIdInteral(id).clone();
    }

    private Ticket getTicketByIdInteral(int id) throws TicketException {
        if (!restTicketStore.containsKey(id)) {
            throw new TicketException("Ticket ID is unknown");
        }

        return restTicketStore.get(id);
    }

    @Override
    public Ticket acceptTicket(int id) throws TicketException {
        Ticket clientTicket = getTicketByIdInteral(id);
        clientTicket.setStatus(Status.ACCEPTED);
        Entity<Ticket> entity = Entity.json(clientTicket);

        // send request and handle response
        final Response response = webTarget
                .path("/tickets/" + String.valueOf(id))
                .request(MediaType.APPLICATION_XML)
                .header("Content-Type", "application/json") // in POSTs/PUTs, content-type tells the server what is sent
                .put(entity);

        // TODO when server not running / has internal error
        Ticket serverTicket = response.readEntity(Ticket.class);
        restTicketStore.replace(serverTicket.getId(), serverTicket);

        if (response.getStatus() == 409)  // sent when a request conflicts with current state of server
            // put a warning dialog instead of "throw new TicketException" b/c the later one prevents the interface from updating
            JOptionPane.showMessageDialog(null, "Ticket " + serverTicket.getId() + " already modified to "
                    + serverTicket.getStatus() + " by another client.\nStatus will be refreshed.", "Ticket already modified", 2);

        if (response.getStatus() == 200)  // OK
            System.out.println("Ticket " + serverTicket.getId() + " successfully set to ACCEPTED");

        return (Ticket) serverTicket.clone();
    }

    @Override
    public Ticket rejectTicket(int id) throws TicketException {
        Ticket clientTicket = getTicketByIdInteral(id);
        clientTicket.setStatus(Status.REJECTED);
        Entity<Ticket> entity = Entity.json(clientTicket);

        // send request and handle response
        final Response response = webTarget
                .path("/tickets/" + String.valueOf(id))
                .request(MediaType.APPLICATION_XML)
                .header("Content-Type", "application/json") // in POSTs/PUTs, content-type tells the server what is sent
                .put(entity);

        // TODO when server not running / has internal error
        Ticket serverTicket = response.readEntity(Ticket.class);
        restTicketStore.replace(serverTicket.getId(), serverTicket);

        if (response.getStatus() == 409)  // sent when a request conflicts with current state of server
            // put a warning dialog instead of "throw new TicketException" b/c the later one prevents the interface from updating
            JOptionPane.showMessageDialog(null, "Ticket " + serverTicket.getId() + " already modified to "
                    + serverTicket.getStatus() + " by another client.\nStatus will be refreshed.", "Ticket already modified", 2);

        if (response.getStatus() == 200)  // OK
            System.out.println("Ticket " + serverTicket.getId() + " successfully set to ACCEPTED");

        return (Ticket) serverTicket.clone();
    }


    @Override
    public Ticket closeTicket(int id) throws TicketException {
        Ticket clientTicket = getTicketByIdInteral(id);
        clientTicket.setStatus(Status.CLOSED);
        Entity<Ticket> entity = Entity.json(clientTicket);

        // send request and handle response
        final Response response = webTarget
                .path("/tickets/" + String.valueOf(id))
                .request(MediaType.APPLICATION_XML)
                .header("Content-Type", "application/json") // in POSTs/PUTs, content-type tells the server what is sent
                .put(entity);

        // TODO when server not running / has internal error
        Ticket serverTicket = response.readEntity(Ticket.class);
        restTicketStore.replace(serverTicket.getId(), serverTicket);

        if (response.getStatus() == 409)  // sent when a request conflicts with current state of server
            // put a warning dialog instead of "throw new TicketException" b/c the later one prevents the interface from updating
            JOptionPane.showMessageDialog(null, "Ticket " + serverTicket.getId() + " already modified to "
                    + serverTicket.getStatus() + " by another client.\nStatus will be refreshed.", "Ticket already modified", 2);

        if (response.getStatus() == 200)  // OK
            System.out.println("Ticket " + serverTicket.getId() + " successfully set to ACCEPTED");

        return (Ticket) serverTicket.clone();
    }

    /**
     * @param text The reporter-/topic-/description-text to be searched
     * @return list of all tickets that contain given text in reporter, topic, or description;
     * or all when text is empty.
     */
    @Override
    public List<Ticket> getTicketsByName(String text, int offset, int limit) {
        String textNoSpace = text.stripLeading().stripTrailing();
        Response response = webTarget
                .path("/search")
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .queryParam("text", textNoSpace)
                .request(MediaType.APPLICATION_XML)
                .get();

        List<Ticket> searchResults = response.readEntity(new GenericType<List<Ticket>>() {});
        return searchResults;
    }

    /**
     * Calls getTicketsByName first, and then filters by Type.
     * @param text The reporter-/topic-/description-text to be searched
     * @param type The {@link Type} of the ticket to be searched
     * @return List of tickets with given reporter/topic/description and type
     */
    @Override
    public List<Ticket> getTicketsByNameAndType(String text, Type type, int offset, int limit) {
        String textNoSpace = text.stripLeading().stripTrailing();
        Response response = webTarget
                .path("/search")
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .queryParam("text", textNoSpace)
                .queryParam("type", type)
                .request(MediaType.APPLICATION_XML)
                .get();

        List<Ticket> searchResults = response.readEntity(new GenericType<List<Ticket>>() {});
        return searchResults;
    }

    @Override
    public void triggerShutdown() {
        client.close();
    }
}
