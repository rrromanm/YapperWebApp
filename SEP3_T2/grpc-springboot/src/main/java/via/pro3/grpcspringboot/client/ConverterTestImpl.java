package via.pro3.grpcspringboot.client;

import io.grpc.stub.StreamObserver;
import org.baeldung.grpc.RequestText;
import org.baeldung.grpc.ResponseText;
import org.baeldung.grpc.TextConverterGrpc;

public class ConverterTestImpl extends TextConverterGrpc.TextConverterImplBase {
    @Override
    public void toUpper(RequestText request, StreamObserver<ResponseText> responseObserver) {
        String inputText = request.getInputText();String outputText = inputText.toUpperCase();

        ResponseText response = ResponseText.newBuilder()
                .setOutputText(outputText)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void capitalizeFirstCharacter(RequestText request, StreamObserver<ResponseText> responseObserver) {
        String inputText = request.getInputText();String outputText = Character.toUpperCase(inputText.charAt(0)) + inputText.substring(1);

        ResponseText response = ResponseText.newBuilder()
                .setOutputText(outputText)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}