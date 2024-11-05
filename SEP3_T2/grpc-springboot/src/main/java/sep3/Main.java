package sep3;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import sep3.service.PostImpl;
import sep3.service.SMUserImpl;


public class Main {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
                .addService(new SMUserImpl())
                .addService(new PostImpl())
                .build()
                .start();

        System.out.println("Server started, listening on " + server.getPort());
        server.awaitTermination();
    }
}
