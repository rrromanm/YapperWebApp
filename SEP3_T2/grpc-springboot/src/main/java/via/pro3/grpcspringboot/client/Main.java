package via.pro3.grpcspringboot.client;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder
                .forPort(9090)
                .addService(new ConverterTestImpl()).build();

        server.start();
        server.awaitTermination();
    }
}
