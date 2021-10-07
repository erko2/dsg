package de.uniba.rz.io.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: ticketManagement.proto")
public final class TicketManagementServiceGrpc {

  private TicketManagementServiceGrpc() {}

  public static final String SERVICE_NAME = "TicketManagementService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.CreateTicketTransferObject,
      de.uniba.rz.io.rpc.TicketTransferObject> getCreateNewTicketMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createNewTicket",
      requestType = de.uniba.rz.io.rpc.CreateTicketTransferObject.class,
      responseType = de.uniba.rz.io.rpc.TicketTransferObject.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.CreateTicketTransferObject,
      de.uniba.rz.io.rpc.TicketTransferObject> getCreateNewTicketMethod() {
    io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.CreateTicketTransferObject, de.uniba.rz.io.rpc.TicketTransferObject> getCreateNewTicketMethod;
    if ((getCreateNewTicketMethod = TicketManagementServiceGrpc.getCreateNewTicketMethod) == null) {
      synchronized (TicketManagementServiceGrpc.class) {
        if ((getCreateNewTicketMethod = TicketManagementServiceGrpc.getCreateNewTicketMethod) == null) {
          TicketManagementServiceGrpc.getCreateNewTicketMethod = getCreateNewTicketMethod = 
              io.grpc.MethodDescriptor.<de.uniba.rz.io.rpc.CreateTicketTransferObject, de.uniba.rz.io.rpc.TicketTransferObject>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "TicketManagementService", "createNewTicket"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.CreateTicketTransferObject.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketTransferObject.getDefaultInstance()))
                  .setSchemaDescriptor(new TicketManagementServiceMethodDescriptorSupplier("createNewTicket"))
                  .build();
          }
        }
     }
     return getCreateNewTicketMethod;
  }

  private static volatile io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketRequest,
      de.uniba.rz.io.rpc.TicketTransferObject> getGetAllTicketsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllTickets",
      requestType = de.uniba.rz.io.rpc.TicketRequest.class,
      responseType = de.uniba.rz.io.rpc.TicketTransferObject.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketRequest,
      de.uniba.rz.io.rpc.TicketTransferObject> getGetAllTicketsMethod() {
    io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketRequest, de.uniba.rz.io.rpc.TicketTransferObject> getGetAllTicketsMethod;
    if ((getGetAllTicketsMethod = TicketManagementServiceGrpc.getGetAllTicketsMethod) == null) {
      synchronized (TicketManagementServiceGrpc.class) {
        if ((getGetAllTicketsMethod = TicketManagementServiceGrpc.getGetAllTicketsMethod) == null) {
          TicketManagementServiceGrpc.getGetAllTicketsMethod = getGetAllTicketsMethod = 
              io.grpc.MethodDescriptor.<de.uniba.rz.io.rpc.TicketRequest, de.uniba.rz.io.rpc.TicketTransferObject>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "TicketManagementService", "getAllTickets"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketTransferObject.getDefaultInstance()))
                  .setSchemaDescriptor(new TicketManagementServiceMethodDescriptorSupplier("getAllTickets"))
                  .build();
          }
        }
     }
     return getGetAllTicketsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId,
      de.uniba.rz.io.rpc.TicketTransferObject> getAcceptTicketMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "acceptTicket",
      requestType = de.uniba.rz.io.rpc.TicketId.class,
      responseType = de.uniba.rz.io.rpc.TicketTransferObject.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId,
      de.uniba.rz.io.rpc.TicketTransferObject> getAcceptTicketMethod() {
    io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId, de.uniba.rz.io.rpc.TicketTransferObject> getAcceptTicketMethod;
    if ((getAcceptTicketMethod = TicketManagementServiceGrpc.getAcceptTicketMethod) == null) {
      synchronized (TicketManagementServiceGrpc.class) {
        if ((getAcceptTicketMethod = TicketManagementServiceGrpc.getAcceptTicketMethod) == null) {
          TicketManagementServiceGrpc.getAcceptTicketMethod = getAcceptTicketMethod = 
              io.grpc.MethodDescriptor.<de.uniba.rz.io.rpc.TicketId, de.uniba.rz.io.rpc.TicketTransferObject>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "TicketManagementService", "acceptTicket"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketTransferObject.getDefaultInstance()))
                  .setSchemaDescriptor(new TicketManagementServiceMethodDescriptorSupplier("acceptTicket"))
                  .build();
          }
        }
     }
     return getAcceptTicketMethod;
  }

  private static volatile io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId,
      de.uniba.rz.io.rpc.TicketTransferObject> getRejectTicketMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "rejectTicket",
      requestType = de.uniba.rz.io.rpc.TicketId.class,
      responseType = de.uniba.rz.io.rpc.TicketTransferObject.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId,
      de.uniba.rz.io.rpc.TicketTransferObject> getRejectTicketMethod() {
    io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId, de.uniba.rz.io.rpc.TicketTransferObject> getRejectTicketMethod;
    if ((getRejectTicketMethod = TicketManagementServiceGrpc.getRejectTicketMethod) == null) {
      synchronized (TicketManagementServiceGrpc.class) {
        if ((getRejectTicketMethod = TicketManagementServiceGrpc.getRejectTicketMethod) == null) {
          TicketManagementServiceGrpc.getRejectTicketMethod = getRejectTicketMethod = 
              io.grpc.MethodDescriptor.<de.uniba.rz.io.rpc.TicketId, de.uniba.rz.io.rpc.TicketTransferObject>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "TicketManagementService", "rejectTicket"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketTransferObject.getDefaultInstance()))
                  .setSchemaDescriptor(new TicketManagementServiceMethodDescriptorSupplier("rejectTicket"))
                  .build();
          }
        }
     }
     return getRejectTicketMethod;
  }

  private static volatile io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId,
      de.uniba.rz.io.rpc.TicketTransferObject> getCloseTicketMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "closeTicket",
      requestType = de.uniba.rz.io.rpc.TicketId.class,
      responseType = de.uniba.rz.io.rpc.TicketTransferObject.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId,
      de.uniba.rz.io.rpc.TicketTransferObject> getCloseTicketMethod() {
    io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.TicketId, de.uniba.rz.io.rpc.TicketTransferObject> getCloseTicketMethod;
    if ((getCloseTicketMethod = TicketManagementServiceGrpc.getCloseTicketMethod) == null) {
      synchronized (TicketManagementServiceGrpc.class) {
        if ((getCloseTicketMethod = TicketManagementServiceGrpc.getCloseTicketMethod) == null) {
          TicketManagementServiceGrpc.getCloseTicketMethod = getCloseTicketMethod = 
              io.grpc.MethodDescriptor.<de.uniba.rz.io.rpc.TicketId, de.uniba.rz.io.rpc.TicketTransferObject>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "TicketManagementService", "closeTicket"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketTransferObject.getDefaultInstance()))
                  .setSchemaDescriptor(new TicketManagementServiceMethodDescriptorSupplier("closeTicket"))
                  .build();
          }
        }
     }
     return getCloseTicketMethod;
  }

  private static volatile io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.AutoUpdateRequest,
      de.uniba.rz.io.rpc.TicketTransferObject> getStartAutoUpdatingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartAutoUpdating",
      requestType = de.uniba.rz.io.rpc.AutoUpdateRequest.class,
      responseType = de.uniba.rz.io.rpc.TicketTransferObject.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.AutoUpdateRequest,
      de.uniba.rz.io.rpc.TicketTransferObject> getStartAutoUpdatingMethod() {
    io.grpc.MethodDescriptor<de.uniba.rz.io.rpc.AutoUpdateRequest, de.uniba.rz.io.rpc.TicketTransferObject> getStartAutoUpdatingMethod;
    if ((getStartAutoUpdatingMethod = TicketManagementServiceGrpc.getStartAutoUpdatingMethod) == null) {
      synchronized (TicketManagementServiceGrpc.class) {
        if ((getStartAutoUpdatingMethod = TicketManagementServiceGrpc.getStartAutoUpdatingMethod) == null) {
          TicketManagementServiceGrpc.getStartAutoUpdatingMethod = getStartAutoUpdatingMethod = 
              io.grpc.MethodDescriptor.<de.uniba.rz.io.rpc.AutoUpdateRequest, de.uniba.rz.io.rpc.TicketTransferObject>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "TicketManagementService", "StartAutoUpdating"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.AutoUpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  de.uniba.rz.io.rpc.TicketTransferObject.getDefaultInstance()))
                  .setSchemaDescriptor(new TicketManagementServiceMethodDescriptorSupplier("StartAutoUpdating"))
                  .build();
          }
        }
     }
     return getStartAutoUpdatingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TicketManagementServiceStub newStub(io.grpc.Channel channel) {
    return new TicketManagementServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TicketManagementServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TicketManagementServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TicketManagementServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TicketManagementServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class TicketManagementServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createNewTicket(de.uniba.rz.io.rpc.CreateTicketTransferObject request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateNewTicketMethod(), responseObserver);
    }

    /**
     */
    public void getAllTickets(de.uniba.rz.io.rpc.TicketRequest request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllTicketsMethod(), responseObserver);
    }

    /**
     */
    public void acceptTicket(de.uniba.rz.io.rpc.TicketId request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnimplementedUnaryCall(getAcceptTicketMethod(), responseObserver);
    }

    /**
     */
    public void rejectTicket(de.uniba.rz.io.rpc.TicketId request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnimplementedUnaryCall(getRejectTicketMethod(), responseObserver);
    }

    /**
     */
    public void closeTicket(de.uniba.rz.io.rpc.TicketId request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnimplementedUnaryCall(getCloseTicketMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.AutoUpdateRequest> startAutoUpdating(
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      return asyncUnimplementedStreamingCall(getStartAutoUpdatingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateNewTicketMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                de.uniba.rz.io.rpc.CreateTicketTransferObject,
                de.uniba.rz.io.rpc.TicketTransferObject>(
                  this, METHODID_CREATE_NEW_TICKET)))
          .addMethod(
            getGetAllTicketsMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                de.uniba.rz.io.rpc.TicketRequest,
                de.uniba.rz.io.rpc.TicketTransferObject>(
                  this, METHODID_GET_ALL_TICKETS)))
          .addMethod(
            getAcceptTicketMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                de.uniba.rz.io.rpc.TicketId,
                de.uniba.rz.io.rpc.TicketTransferObject>(
                  this, METHODID_ACCEPT_TICKET)))
          .addMethod(
            getRejectTicketMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                de.uniba.rz.io.rpc.TicketId,
                de.uniba.rz.io.rpc.TicketTransferObject>(
                  this, METHODID_REJECT_TICKET)))
          .addMethod(
            getCloseTicketMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                de.uniba.rz.io.rpc.TicketId,
                de.uniba.rz.io.rpc.TicketTransferObject>(
                  this, METHODID_CLOSE_TICKET)))
          .addMethod(
            getStartAutoUpdatingMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                de.uniba.rz.io.rpc.AutoUpdateRequest,
                de.uniba.rz.io.rpc.TicketTransferObject>(
                  this, METHODID_START_AUTO_UPDATING)))
          .build();
    }
  }

  /**
   */
  public static final class TicketManagementServiceStub extends io.grpc.stub.AbstractStub<TicketManagementServiceStub> {
    private TicketManagementServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TicketManagementServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TicketManagementServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TicketManagementServiceStub(channel, callOptions);
    }

    /**
     */
    public void createNewTicket(de.uniba.rz.io.rpc.CreateTicketTransferObject request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateNewTicketMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllTickets(de.uniba.rz.io.rpc.TicketRequest request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetAllTicketsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void acceptTicket(de.uniba.rz.io.rpc.TicketId request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAcceptTicketMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rejectTicket(de.uniba.rz.io.rpc.TicketId request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRejectTicketMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void closeTicket(de.uniba.rz.io.rpc.TicketId request,
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCloseTicketMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.AutoUpdateRequest> startAutoUpdating(
        io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getStartAutoUpdatingMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class TicketManagementServiceBlockingStub extends io.grpc.stub.AbstractStub<TicketManagementServiceBlockingStub> {
    private TicketManagementServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TicketManagementServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TicketManagementServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TicketManagementServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public de.uniba.rz.io.rpc.TicketTransferObject createNewTicket(de.uniba.rz.io.rpc.CreateTicketTransferObject request) {
      return blockingUnaryCall(
          getChannel(), getCreateNewTicketMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<de.uniba.rz.io.rpc.TicketTransferObject> getAllTickets(
        de.uniba.rz.io.rpc.TicketRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetAllTicketsMethod(), getCallOptions(), request);
    }

    /**
     */
    public de.uniba.rz.io.rpc.TicketTransferObject acceptTicket(de.uniba.rz.io.rpc.TicketId request) {
      return blockingUnaryCall(
          getChannel(), getAcceptTicketMethod(), getCallOptions(), request);
    }

    /**
     */
    public de.uniba.rz.io.rpc.TicketTransferObject rejectTicket(de.uniba.rz.io.rpc.TicketId request) {
      return blockingUnaryCall(
          getChannel(), getRejectTicketMethod(), getCallOptions(), request);
    }

    /**
     */
    public de.uniba.rz.io.rpc.TicketTransferObject closeTicket(de.uniba.rz.io.rpc.TicketId request) {
      return blockingUnaryCall(
          getChannel(), getCloseTicketMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TicketManagementServiceFutureStub extends io.grpc.stub.AbstractStub<TicketManagementServiceFutureStub> {
    private TicketManagementServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TicketManagementServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TicketManagementServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TicketManagementServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<de.uniba.rz.io.rpc.TicketTransferObject> createNewTicket(
        de.uniba.rz.io.rpc.CreateTicketTransferObject request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateNewTicketMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<de.uniba.rz.io.rpc.TicketTransferObject> acceptTicket(
        de.uniba.rz.io.rpc.TicketId request) {
      return futureUnaryCall(
          getChannel().newCall(getAcceptTicketMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<de.uniba.rz.io.rpc.TicketTransferObject> rejectTicket(
        de.uniba.rz.io.rpc.TicketId request) {
      return futureUnaryCall(
          getChannel().newCall(getRejectTicketMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<de.uniba.rz.io.rpc.TicketTransferObject> closeTicket(
        de.uniba.rz.io.rpc.TicketId request) {
      return futureUnaryCall(
          getChannel().newCall(getCloseTicketMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_NEW_TICKET = 0;
  private static final int METHODID_GET_ALL_TICKETS = 1;
  private static final int METHODID_ACCEPT_TICKET = 2;
  private static final int METHODID_REJECT_TICKET = 3;
  private static final int METHODID_CLOSE_TICKET = 4;
  private static final int METHODID_START_AUTO_UPDATING = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TicketManagementServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TicketManagementServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_NEW_TICKET:
          serviceImpl.createNewTicket((de.uniba.rz.io.rpc.CreateTicketTransferObject) request,
              (io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject>) responseObserver);
          break;
        case METHODID_GET_ALL_TICKETS:
          serviceImpl.getAllTickets((de.uniba.rz.io.rpc.TicketRequest) request,
              (io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject>) responseObserver);
          break;
        case METHODID_ACCEPT_TICKET:
          serviceImpl.acceptTicket((de.uniba.rz.io.rpc.TicketId) request,
              (io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject>) responseObserver);
          break;
        case METHODID_REJECT_TICKET:
          serviceImpl.rejectTicket((de.uniba.rz.io.rpc.TicketId) request,
              (io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject>) responseObserver);
          break;
        case METHODID_CLOSE_TICKET:
          serviceImpl.closeTicket((de.uniba.rz.io.rpc.TicketId) request,
              (io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_START_AUTO_UPDATING:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.startAutoUpdating(
              (io.grpc.stub.StreamObserver<de.uniba.rz.io.rpc.TicketTransferObject>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TicketManagementServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TicketManagementServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return de.uniba.rz.io.rpc.TicketManagement.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TicketManagementService");
    }
  }

  private static final class TicketManagementServiceFileDescriptorSupplier
      extends TicketManagementServiceBaseDescriptorSupplier {
    TicketManagementServiceFileDescriptorSupplier() {}
  }

  private static final class TicketManagementServiceMethodDescriptorSupplier
      extends TicketManagementServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TicketManagementServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TicketManagementServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TicketManagementServiceFileDescriptorSupplier())
              .addMethod(getCreateNewTicketMethod())
              .addMethod(getGetAllTicketsMethod())
              .addMethod(getAcceptTicketMethod())
              .addMethod(getRejectTicketMethod())
              .addMethod(getCloseTicketMethod())
              .addMethod(getStartAutoUpdatingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
