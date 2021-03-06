// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ticketManagement.proto

package de.uniba.rz.io.rpc;

public final class TicketManagement {
  private TicketManagement() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TicketRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_TicketRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AutoUpdateRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AutoUpdateRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TicketId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_TicketId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TicketTransferObject_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_TicketTransferObject_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CreateTicketTransferObject_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CreateTicketTransferObject_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026ticketManagement.proto\"\017\n\rTicketReques" +
      "t\"u\n\021AutoUpdateRequest\0223\n\013requestType\030\001 " +
      "\001(\0162\036.AutoUpdateRequest.RequestType\"+\n\013R" +
      "equestType\022\014\n\010REGISTER\020\000\022\016\n\nDEREGISTER\020\001" +
      "\"\026\n\010TicketId\022\n\n\002id\030\001 \001(\005\"\243\001\n\024TicketTrans" +
      "ferObject\022\n\n\002id\030\001 \001(\005\022\020\n\010reporter\030\002 \001(\t\022" +
      "\r\n\005topic\030\003 \001(\t\022\023\n\013description\030\004 \001(\t\022\023\n\004t" +
      "ype\030\005 \001(\0162\005.Type\022\033\n\010priority\030\006 \001(\0162\t.Pri" +
      "ority\022\027\n\006status\030\007 \001(\0162\007.Status\"\235\001\n\032Creat" +
      "eTicketTransferObject\022\020\n\010reporter\030\001 \001(\t\022" +
      "\r\n\005topic\030\002 \001(\t\022\023\n\013description\030\003 \001(\t\022\023\n\004t" +
      "ype\030\004 \001(\0162\005.Type\022\033\n\010priority\030\005 \001(\0162\t.Pri" +
      "ority\022\027\n\006status\030\006 \001(\0162\007.Status*8\n\004Type\022\010" +
      "\n\004TASK\020\000\022\017\n\013ENHANCEMENT\020\001\022\007\n\003BUG\020\002\022\014\n\010QU" +
      "ESTION\020\003*.\n\010Priority\022\014\n\010CRITICAL\020\000\022\t\n\005MA" +
      "JOR\020\001\022\t\n\005MINOR\020\002*9\n\006Status\022\007\n\003NEW\020\000\022\014\n\010A" +
      "CCEPTED\020\001\022\014\n\010REJECTED\020\002\022\n\n\006CLOSED\020\0032\363\002\n\027" +
      "TicketManagementService\022E\n\017createNewTick" +
      "et\022\033.CreateTicketTransferObject\032\025.Ticket" +
      "TransferObject\0228\n\rgetAllTickets\022\016.Ticket" +
      "Request\032\025.TicketTransferObject0\001\0220\n\014acce" +
      "ptTicket\022\t.TicketId\032\025.TicketTransferObje" +
      "ct\0220\n\014rejectTicket\022\t.TicketId\032\025.TicketTr" +
      "ansferObject\022/\n\013closeTicket\022\t.TicketId\032\025" +
      ".TicketTransferObject\022B\n\021StartAutoUpdati" +
      "ng\022\022.AutoUpdateRequest\032\025.TicketTransferO" +
      "bject(\0010\001B.\n\022de.uniba.rz.io.rpcB\020TicketM" +
      "anagementP\001\242\002\003RTGb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_TicketRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_TicketRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_TicketRequest_descriptor,
        new java.lang.String[] { });
    internal_static_AutoUpdateRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_AutoUpdateRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AutoUpdateRequest_descriptor,
        new java.lang.String[] { "RequestType", });
    internal_static_TicketId_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_TicketId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_TicketId_descriptor,
        new java.lang.String[] { "Id", });
    internal_static_TicketTransferObject_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_TicketTransferObject_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_TicketTransferObject_descriptor,
        new java.lang.String[] { "Id", "Reporter", "Topic", "Description", "Type", "Priority", "Status", });
    internal_static_CreateTicketTransferObject_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_CreateTicketTransferObject_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CreateTicketTransferObject_descriptor,
        new java.lang.String[] { "Reporter", "Topic", "Description", "Type", "Priority", "Status", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
