package sep3;

import io.grpc.stub.StreamObserver;
import org.baeldung.grpc.RequestText;
import org.baeldung.grpc.ResponseText;
import org.baeldung.grpc.TextConverterGrpc;

public class ConverterTestImpl extends TextConverterGrpc.TextConverterImplBase {
    public void toUpper(RequestText request, StreamObserver<ResponseText> responseObserver) {
        String inputText = request.getInputText();
        String outputText = inputText.toUpperCase();

        System.out.println(inputText);

        ResponseText response = ResponseText.newBuilder()
                .setOutputText(outputText)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}