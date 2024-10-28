package via.pro3.grpcspringboot.client;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(9090)
                .addService(new ConverterTestImpl())
                .build()
                .start();

        System.out.println("Server started, listening on " + 9090);
        server.awaitTermination();
    }
}
