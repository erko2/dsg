package de.uniba.rz.models;

import de.uniba.rz.entities.Ticket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class Deserializer {

    private Ticket deserializedTicket;
    private List<Ticket> deserializedTicketList;

    public Ticket deserialize(byte[] ticketData) {

        try(ByteArrayInputStream in = new ByteArrayInputStream(ticketData);
            ObjectInputStream is = new ObjectInputStream(in)) {

            deserializedTicket = (Ticket) is.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return deserializedTicket;
    }

    public List<Ticket> deserializeList(byte[] ticketListData) {

        try(ByteArrayInputStream in = new ByteArrayInputStream(ticketListData);
            ObjectInputStream is = new ObjectInputStream(in)) {

            deserializedTicketList = (List<Ticket>) is.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return deserializedTicketList;
    }

}
