package de.uniba.rz.backend.rest.post;

import de.uniba.rz.backend.MyTicketStore;
import de.uniba.rz.backend.TicketStore;
import de.uniba.rz.entities.Ticket;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
public class Create {
    TicketStore ticketStoreReference = MyTicketStore.getInstance();

    // give an internal id to each object that is created
    // for debugging purposes
    private static final AtomicInteger next_id = new AtomicInteger(1);
    private final int objectId = next_id.getAndIncrement();

    private static boolean stop = false;    // for concurrency testing

    public Create() {
        // to see when/how many times the annotation system instantiates this class
        System.out.println(this.getClass().getSimpleName() + ".class object with internal id " + objectId + " created");
    }

    @POST
    public Response createTicket(Ticket newTicket, @Context UriInfo uriInfo) {
        System.out.println("Create.createNewTicket(). Object id " + objectId);

        if (newTicket == null) {
            throw new WebApplicationException("Invalid request body" , 400);
        }

        Ticket storedTicket = ticketStoreReference.storeNewTicket(
                newTicket.getReporter(),
                newTicket.getTopic(),
                newTicket.getDescription(),
                newTicket.getType(),
                newTicket.getPriority());

        UriBuilder path = uriInfo.getAbsolutePathBuilder();
        path.path(Integer.toString(storedTicket.getId()));
        return Response.created(path.build()).entity(storedTicket).build();
    }
}
