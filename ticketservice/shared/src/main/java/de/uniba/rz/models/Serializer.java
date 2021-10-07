package de.uniba.rz.models;

import de.uniba.rz.entities.Ticket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class Serializer {

    private byte[] serializedticketData;
    private byte[] serializedticketListData;

    public byte[] serialize(Ticket ticket) {

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); ObjectOutputStream os = new ObjectOutputStream(outputStream)) {
            os.writeObject(ticket);
            serializedticketData = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serializedticketData;
    }

    public byte[] serializeList(List<Ticket> ticketList) {

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); ObjectOutputStream os = new ObjectOutputStream(outputStream)) {
            os.writeObject(ticketList);
            serializedticketListData = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serializedticketListData;
    }
}
