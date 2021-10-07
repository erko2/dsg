package de.uniba.rz.backend.rest.get;

import de.uniba.rz.backend.MyTicketStore;
import de.uniba.rz.backend.TicketStore;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.entities.Type;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * On concurrency: a new instance of this class will be created for each request, so there is no need for manual thread creation.
 * See https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/user-guide.html#d0e2691
 * Only the common resource access (MyTicketStore) should be handled, which it already is.
 */
@Path("search")
public class Search {
    TicketStore ticketStoreReference = MyTicketStore.getInstance();

    /**
     * @param offset Partition, i.e the nth sublist of tickets
     * @param limit Partition size
     * @param text Text to search for in ticket-report/topic/description
     * @param type Ticket-type to search for
     * @return The nth sublist of tickets, matching text and type, ordered by priority
     */
    @GET
    public List<Ticket> getTicketsByText(@QueryParam("offset") @DefaultValue("1") int offset,
                                         @QueryParam("limit") @DefaultValue("10") int limit,
                                         @QueryParam("text") @DefaultValue("") String text,
                                         @QueryParam("type") @DefaultValue("") Type type) {
        if(offset == 0)
            offset = 1;

        final List<Ticket> allTickets = ticketStoreReference.getAllTickets();
        List<Ticket> searchResults = new ArrayList();

        // skip the matching tickets of the previous partitions and add till the end of the current partition
        // examples: offset=1 limit=10 => get 1 partition of size 10 => collect 1st till 10th matching ticket
        //           offset 2 limit=5 => get 2 partition of size 5 => collect 6th till 10th matching ticket
        int matchingTicketCounter = 0;
        Iterator<Ticket> iter = allTickets.iterator();
        while (iter.hasNext() && (searchResults.size() < limit)) {
            Ticket ticket = iter.next();

            // matching ticket conditions: 1) given text is empty OR text is contained in reporter/topic/description
            if ( (text.equals("") || ticket.getReporter().contains(text) || ticket.getTopic().contains(text) || ticket.getDescription().contains(text))
                    // 2) AND given type is null OR matches the one of the ticket
                    && (type == null || ticket.getType() == type) ) {
                matchingTicketCounter++;

                // add only after skipping previous partitions first
                if ( matchingTicketCounter >= (offset-1) * limit + 1 ) {
                    searchResults.add(ticket);
                }
            }
        }

        Collections.sort(searchResults, (Ticket t1, Ticket t2) -> t1.getPriority().compareTo(t2.getPriority()));

        return searchResults;
    }
}
