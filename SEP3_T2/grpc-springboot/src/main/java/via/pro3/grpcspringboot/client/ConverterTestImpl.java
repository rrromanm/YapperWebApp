package via.pro3.grpcspringboot.client;

import io.grpc.stub.StreamObserver;
import via.pro3.grpcspringbootexample.grpc.RequestText;
import via.pro3.grpcspringbootexample.grpc.ResponseText;
import via.pro3.grpcspringbootexample.grpc.TextConverterGrpc;

public class ConverterTestImpl extends TextConverterGrpc.TextConverterImplBase {
    public void sendText(RequestText request, StreamObserver<ResponseText> responseObserver) {

        String text = new StringBuilder()
                .append("Upper, ")
                .append(request.getInputText())
                .append(" ")
                .toString();

        ResponseText response = ResponseText.newBuilder()
                .setOutputText(text)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
