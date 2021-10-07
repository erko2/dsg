package de.uniba.rz.backend.rest.put;

import de.uniba.rz.backend.MyTicketStore;
import de.uniba.rz.backend.TicketStore;
import de.uniba.rz.backend.UnknownTicketException;
import de.uniba.rz.entities.Ticket;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * On concurrency: a new instance of this class will be created for each request, so there is no need for manual thread creation.
 * See https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/user-guide.html#d0e2691
 * Only the common resource access (MyTicketStore) should be handled, which it already is.
 */
@Path("tickets")
public class Update {
    TicketStore ticketStoreReference = MyTicketStore.getInstance();

    // give an internal id to each object that is created
    // for debugging purposes
    private static final AtomicInteger next_id = new AtomicInteger(1);
    private final int objectId = next_id.getAndIncrement();

    public Update() {
        // to see when/how many times the annotation system instantiates this class
        System.out.println(this.getClass().getSimpleName() + ".class object with internal id " + objectId + " created");
    }

    @PUT
    @Path("{ticket-id}")
    public Response updateTicket(@PathParam("ticket-id") int id, Ticket clientTicket, @Context UriInfo uriInfo) {
        System.out.println("Update.updateTicket(). Object id " + objectId);

        try {
            if (clientTicket == null) {
                throw new WebApplicationException("Invalid request body", 400);
            }

            ticketStoreReference.updateTicketStatus(id, clientTicket.getStatus());
            Ticket storedTicket = ticketStoreReference.getTicket(id);

            UriBuilder path = uriInfo.getAbsolutePathBuilder();

            if (storedTicket.getStatus() == clientTicket.getStatus())
                return Response.ok().contentLocation(path.build()).entity(storedTicket).build();
            else
                // 409 sent when a request conflicts with current state of server
                return Response.status(409).contentLocation(path.build()).entity(storedTicket).build();
        }
        catch (UnknownTicketException e) {
            throw new WebApplicationException("No ticket found with id: " + id, 404);
        }
    }
}
