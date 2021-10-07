package de.uniba.rz.backend;

import de.uniba.rz.converter.TicketTransferConverter;
import de.uniba.rz.entities.Priority;
import de.uniba.rz.entities.Status;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.entities.Type;
import de.uniba.rz.io.rpc.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

public class GRPCRemoteAccess implements RemoteAccess {

    private final Server server;
    private final int port;
    private static Set<StreamObserver<TicketTransferObject>> observers = ConcurrentHashMap.newKeySet();

    /**
     * During the construction of our server, the implemented service must be specified. There is also a possibility to
     * expose more than one service.
     */
    public GRPCRemoteAccess() {
        this.port = 8999;
        this.server = ServerBuilder.forPort(port).addService(new TicketManagementServiceImpl()).build();
    }

    @Override
    public void prepareStartup(TicketStore ticketStore) throws IOException, TimeoutException {
    }

    /**
     * Starts the server and adds a shutdown hock to orderly shutdown the server.
     *
     * @throws IOException
     */
    @Override
    public void run() {
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started and listened on port " + this.port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                GRPCRemoteAccess.this.shutdown();
                System.err.println("*** server shut down");
            }
        });
    }

    /**
     * Method to stop the server, if a server is present.
     */
    @Override
    public void shutdown() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Blocking method until the shutdown hock terminates the server.
     *
     * @throws InterruptedException
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Custom class for the implementation of the base service, which is an abstract class. The method must be
     * overridden since the default implementation has no implemented logic.
     */
    static class TicketManagementServiceImpl extends TicketManagementServiceGrpc.TicketManagementServiceImplBase {
        @Override
        public void createNewTicket(CreateTicketTransferObject request, StreamObserver<TicketTransferObject> responseObserver) {
            System.out.println(request);
            Ticket ticket = MyTicketStore.getInstance().storeNewTicket(
                    request.getReporter(),
                    request.getTopic(),
                    request.getDescription(),
                    Enum.valueOf(Type.class, request.getType().toString()),
                    Enum.valueOf(Priority.class, request.getPriority().toString()));
            broadcastTickets();
            responseObserver.onNext(TicketTransferConverter.convertToTransferObject(ticket));
            responseObserver.onCompleted();
        }

        @Override
        public void getAllTickets(TicketRequest request, StreamObserver<TicketTransferObject> responseObserver) {
            for (Ticket ticket : MyTicketStore.getInstance().getAllTickets()) {
                responseObserver.onNext(TicketTransferConverter.convertToTransferObject(ticket));
            }
            responseObserver.onCompleted();
        }

        @Override
        public void acceptTicket(TicketId ticketId, StreamObserver<TicketTransferObject> responseObserver) {
            try {
                System.out.println("SERVER ID of Ticket to ACCEPT: " + ticketId.getId());
                MyTicketStore.getInstance().updateTicketStatus(ticketId.getId(), Status.ACCEPTED);
                TicketTransferObject ticketTransferObject = TicketTransferConverter.convertToTransferObject(MyTicketStore.
                        getInstance().getTicket(ticketId.getId()));
                broadcastTickets();
                responseObserver.onCompleted();
            } catch (UnknownTicketException e) {
                System.out.println("Ticket " + ticketId.getId() + " not found");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void rejectTicket(TicketId ticketId, StreamObserver<TicketTransferObject> responseObserver) {
            System.out.println("SERVER ID of Ticket to REJECT: " + ticketId.getId());
            try {
                MyTicketStore.getInstance().updateTicketStatus(ticketId.getId(), Status.REJECTED);
                TicketTransferObject ticketTransferObject = TicketTransferConverter.convertToTransferObject(MyTicketStore.
                        getInstance().getTicket(ticketId.getId()));
                broadcastTickets();
                responseObserver.onCompleted();
            } catch (UnknownTicketException e) {
                System.out.println("Ticket " + ticketId.getId() + " not found");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void closeTicket(TicketId ticketId, StreamObserver<TicketTransferObject> responseObserver) {
            System.out.println("SERVER ID of Ticket to CLOSE: " + ticketId.getId());
            try {
                MyTicketStore.getInstance().updateTicketStatus(ticketId.getId(), Status.CLOSED);
                TicketTransferObject ticketTransferObject = TicketTransferConverter.convertToTransferObject(MyTicketStore.
                        getInstance().getTicket(ticketId.getId()));
                broadcastTickets();
                responseObserver.onCompleted();
            } catch (UnknownTicketException e) {
                System.out.println("Ticket " + ticketId.getId() + " not found");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        /**
         * Bi-directional communication, which can only be asynchronous. Receives a stream whether the client wants to
         * register or deregister, and responds with all available tickets for the clients.
         *
         * @param responseObserver an observer to receive the stream of tickets.
         * @return an observer to handle requested tickets.
         */

        @Override
        public StreamObserver<AutoUpdateRequest> startAutoUpdating(final StreamObserver<TicketTransferObject> responseObserver) {
            observers.add(responseObserver);
            System.out.println("ADDED OBSERVER: " + responseObserver.toString());

            return new StreamObserver<AutoUpdateRequest>() {
                @Override
                public void onNext(AutoUpdateRequest autoUpdateRequest) {
                    System.out.println("Registering for autoupdate");
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println("SERVER async communication failed");
                    t.printStackTrace();
                }

                @Override
                public void onCompleted() {
                    System.out.println("SERVER async communication finished");
                    observers.remove(responseObserver);
                    System.out.println("REMOVED OBSERVER:" + responseObserver.toString());
                    responseObserver.onCompleted();
                }
            };
        }
    }

    private static void broadcastTickets() {
        for (Ticket ticket : MyTicketStore.getInstance().getAllTickets()) {
            TicketTransferObject ticketTransferObject = TicketTransferConverter.convertToTransferObject(ticket);
            for(StreamObserver observer: observers) {
                observer.onNext(ticketTransferObject);
            }
            System.out.println("SERVER Sending Ticket: " + ticketTransferObject);
        }
    }
}
