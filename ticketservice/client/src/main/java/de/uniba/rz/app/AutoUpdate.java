package de.uniba.rz.app;

import de.uniba.rz.converter.TicketTransferConverter;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.io.rpc.AutoUpdateRequest;
import de.uniba.rz.io.rpc.TicketManagementServiceGrpc;
import de.uniba.rz.io.rpc.TicketTransferObject;
import de.uniba.rz.ui.swing.SwingMainController;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.LinkedList;
import java.util.List;

public class AutoUpdate {

    private final TicketManagementServiceGrpc.TicketManagementServiceStub asyncStub;
    private final ManagedChannel channel;
    private final GRPCTicketManagementBackend backend = new GRPCTicketManagementBackend();
    private SwingMainController controller;
    private StreamObserver<AutoUpdateRequest> requestObserver;

    public StreamObserver<AutoUpdateRequest> getRequestObserver() {
        return requestObserver;
    }

    AutoUpdate() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8999).usePlaintext().build();
        this.asyncStub = TicketManagementServiceGrpc.newStub(this.channel);
    }


    // Set SwingMainController to refresh the Ticket List (set in Main)
    public void setController(SwingMainController controller) {
        this.controller = controller;
    }

    /**
     * Bi-directional communication, which can only be asynchronous. Send a AutoUpdateRequest from the client to the server, and get all
     * tickets back that are stored on the server.
     */
    public void startAutoUpdating() {
        List<Ticket> ticketList = new LinkedList<>();
        requestObserver = asyncStub.startAutoUpdating(new StreamObserver<>() {
            @Override
            public void onNext(TicketTransferObject ticketTransferObject) {

                ticketList.add(TicketTransferConverter.convertToTicket(ticketTransferObject));

                for (Ticket ticket : ticketList) {
                    backend.localTicketStore.put(ticket.getId(), ticket);
                }

                if (!backend.localTicketStore.isEmpty()) {
                    controller.refreshTicketList();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("CLIENT async communication failed");
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("CLIENT async communication finished");
            }
        });

        try {
                requestObserver.onNext(AutoUpdateRequest.newBuilder().setRequestType(AutoUpdateRequest.RequestType.REGISTER).build());

        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        }
    }
}
