package de.uniba.rz.converter;

import de.uniba.rz.entities.Priority;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.entities.Type;
import de.uniba.rz.io.rpc.Status;
import de.uniba.rz.io.rpc.TicketTransferObject;

public class TicketTransferConverter {

    public static TicketTransferObject convertToTransferObject(Ticket ticket) {
        TicketTransferObject ticketTransferObject = TicketTransferObject.newBuilder()
                .setId(ticket.getId())
                .setReporter(ticket.getReporter())
                .setTopic(ticket.getTopic())
                .setDescription(ticket.getDescription())
                .setType(Enum.valueOf(de.uniba.rz.io.rpc.Type.class, ticket.getType().toString()))
                .setPriority(Enum.valueOf(de.uniba.rz.io.rpc.Priority.class, ticket.getPriority().toString()))
                .setStatus(Enum.valueOf(Status.class, ticket.getStatus().toString()))
                .build();
        return ticketTransferObject;
    }

    public static Ticket convertToTicket(TicketTransferObject ticketTransferObject) {
        Ticket ticket = new Ticket(
                ticketTransferObject.getId(),
                ticketTransferObject.getReporter(),
                ticketTransferObject.getTopic(),
                ticketTransferObject.getDescription(),
                Enum.valueOf(Type.class, ticketTransferObject.getType().toString()),
                Enum.valueOf(Priority.class, ticketTransferObject.getPriority().toString()),
                Enum.valueOf(de.uniba.rz.entities.Status.class, ticketTransferObject.getStatus().toString())
        );
        return ticket;
    }
}
