package de.uniba.rz.backend;

import de.uniba.rz.entities.Priority;
import de.uniba.rz.entities.Status;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.entities.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyTicketStore implements TicketStore {

    private AtomicInteger nextTicketId = new AtomicInteger(1);
    private List<Ticket> ticketList = new ArrayList<>();
    private static MyTicketStore instance = new MyTicketStore();

    public static MyTicketStore getInstance(){
        return instance;
    }

    private MyTicketStore(){}

    @Override
    public Ticket storeNewTicket(String reporter, String topic, String description, Type type, Priority priority) {
        Ticket newTicket = new Ticket(nextTicketId.getAndIncrement(), reporter, topic, description, type, priority, Status.NEW);
        ticketList.add(newTicket);
        return newTicket;
    }

    HashMap<Integer,Lock> ticketLocks = new HashMap();

    @Override
    public void updateTicketStatus(int ticketId, Status newStatus) throws UnknownTicketException, IllegalStateException {
            System.out.println("REQUEST to modify status of ticket " + ticketId + " to " + newStatus.toString());

            Lock ticketLock = null;

            if (ticketLocks.containsKey(ticketId)) {
                ticketLock = ticketLocks.get(ticketId);
            }
            else {
                ticketLock = new ReentrantLock();
                ticketLocks.put(ticketId, ticketLock);
            }

            System.out.println("WAITING to acquire Lock for Ticket " + ticketId);
            ticketLock.lock();    // if lock cannot be acquired it waits until it can
            System.out.println("Lock for Ticket " + ticketId + " ACQUIRED");

            boolean ticketFound = false;

            for (Ticket ticket : ticketList) {
                if (ticket.getId() == ticketId) {
                    ticketFound = true;
                    // preventing that a client that hasn't refreshed in some time overwrites the changes of
                    // another client with newer data
                    Status oldStatus = ticket.getStatus();
                    if ( (oldStatus == Status.NEW) || (oldStatus == Status.ACCEPTED && newStatus == Status.CLOSED) )
                        ticket.setStatus(newStatus);
                    else    // old status of REJECTED, CLOSED is not changed
                        System.out.println("Ticket " + ticketId + " already modified to " + oldStatus.toString());
                }
            }

            if (ticketFound == false) {
                throw new UnknownTicketException();
            }

            ticketLock.unlock();    // comment out for testing: if a second modification on the same ticket blocks
            System.out.println("Lock for Ticket " + ticketId + " UNLOCKED");
    }

    @Override
    public List<Ticket> getAllTickets() {
        return Collections.unmodifiableList(ticketList);  // return an unmodifiable(const) reference to ticketList
    }

    /**
     * Returns a copy of the Ticket with given id
     * @param id
     * @return Ticket
     */
    public Ticket getTicket(int id) {
        for (Ticket ticket : ticketList) {
            if (ticket.getId() == id)
                return (Ticket) ticket.clone();
        }
        return null;
    }
}
