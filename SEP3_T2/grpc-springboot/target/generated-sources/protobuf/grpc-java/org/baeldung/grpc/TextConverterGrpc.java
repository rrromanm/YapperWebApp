package org.baeldung.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: TextConverter.proto")
public final class TextConverterGrpc {

  private TextConverterGrpc() {}

  public static final String SERVICE_NAME = "org.baeldung.grpc.TextConverter";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<org.baeldung.grpc.RequestText,
      org.baeldung.grpc.ResponseText> METHOD_TO_UPPER =
      io.grpc.MethodDescriptor.<org.baeldung.grpc.RequestText, org.baeldung.grpc.ResponseText>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "org.baeldung.grpc.TextConverter", "ToUpper"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              org.baeldung.grpc.RequestText.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              org.baeldung.grpc.ResponseText.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TextConverterStub newStub(io.grpc.Channel channel) {
    return new TextConverterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TextConverterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TextConverterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TextConverterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TextConverterFutureStub(channel);
  }

  /**
   */
  public static abstract class TextConverterImplBase implements io.grpc.BindableService {

    /**
     */
    public void toUpper(org.baeldung.grpc.RequestText request,
        io.grpc.stub.StreamObserver<org.baeldung.grpc.ResponseText> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_TO_UPPER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_TO_UPPER,
            asyncUnaryCall(
              new MethodHandlers<
                org.baeldung.grpc.RequestText,
                org.baeldung.grpc.ResponseText>(
                  this, METHODID_TO_UPPER)))
          .build();
    }
  }

  /**
   */
  public static final class TextConverterStub extends io.grpc.stub.AbstractStub<TextConverterStub> {
    private TextConverterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TextConverterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TextConverterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TextConverterStub(channel, callOptions);
    }

    /**
     */
    public void toUpper(org.baeldung.grpc.RequestText request,
        io.grpc.stub.StreamObserver<org.baeldung.grpc.ResponseText> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TO_UPPER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TextConverterBlockingStub extends io.grpc.stub.AbstractStub<TextConverterBlockingStub> {
    private TextConverterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TextConverterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TextConverterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TextConverterBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.baeldung.grpc.ResponseText toUpper(org.baeldung.grpc.RequestText request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TO_UPPER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TextConverterFutureStub extends io.grpc.stub.AbstractStub<TextConverterFutureStub> {
    private TextConverterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TextConverterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TextConverterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TextConverterFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.baeldung.grpc.ResponseText> toUpper(
        org.baeldung.grpc.RequestText request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TO_UPPER, getCallOptions()), request);
    }
  }

  private static final int METHODID_TO_UPPER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TextConverterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TextConverterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TO_UPPER:
          serviceImpl.toUpper((org.baeldung.grpc.RequestText) request,
              (io.grpc.stub.StreamObserver<org.baeldung.grpc.ResponseText>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class TextConverterDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.baeldung.grpc.TextConverterOuterClass.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TextConverterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TextConverterDescriptorSupplier())
              .addMethod(METHOD_TO_UPPER)
              .build();
        }
      }
    }
    return result;
  }
}
