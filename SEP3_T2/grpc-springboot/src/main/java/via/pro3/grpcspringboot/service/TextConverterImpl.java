package via.pro3.grpcspringboot.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.baeldung.grpc.RequestText;
import org.baeldung.grpc.ResponseText;
import org.baeldung.grpc.TextConverterGrpc;

@GrpcService
public class TextConverterImpl extends TextConverterGrpc.TextConverterImplBase {
    @Override
    public void toUpper(RequestText request, StreamObserver<ResponseText> responseObserver) {
        // super.toUpper(request, responseObserver);
        System.out.println("Received Request ??? => " + request.toString());
        ResponseText responseText = ResponseText.newBuilder()
                .setOutputText(request.getInputText().toUpperCase()).build();
        responseObserver.onNext(responseText);
        responseObserver.onCompleted();
    }

    @Override
    public void capitalizeFirstCharacter(RequestText request, StreamObserver<ResponseText> responseObserver) {
        System.out.println("Received Request ??? => " + request.toString());
        String res = Character.toUpperCase(request.getInputText().charAt(0))
                + request.getInputText().substring(1).toLowerCase();
        ResponseText responseText = ResponseText.newBuilder().setOutputText(res).build();
        responseObserver.onNext(responseText);
        responseObserver.onCompleted();
    }
}
