package de.uniba.rz.backend.rest.get;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import de.uniba.rz.backend.MyTicketStore;
import de.uniba.rz.backend.TicketStore;
import de.uniba.rz.entities.Ticket;

/**
 * On concurrency: a new instance of this class will be created for each request, so there is no need for manual thread creation.
 * See https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/user-guide.html#d0e2691
 * Only the common resource access (MyTicketStore) should be handled, which it already is.
 */
@Path("tickets")
public class ReadAll {
    TicketStore ticketStoreReference = MyTicketStore.getInstance();

    // give an internal id to each object that is created
    // for debugging purposes
    private static final AtomicInteger next_id = new AtomicInteger(1);
    private final int objectId = next_id.getAndIncrement();

    public ReadAll() {
        // to see when/how many times the annotation system instantiates this class
        System.out.println(this.getClass().getSimpleName() + ".class object with internal id " + objectId + " created");
    }

    @GET
    public List<Ticket> getTickets() {
        System.out.println("ReadAll.getTickets(). Object id " + objectId);

        final List<Ticket> lst = ticketStoreReference.getAllTickets();
        System.out.println("REST-ReadAll: Number of existing tickets: " + lst.size());

        return lst;
    }
}
