package sep3;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import sep3.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
                .addService(new UserServiceImpl())
                .build()
                .start();

        System.out.println("Server started, listening on " + 8080);
        server.awaitTermination();
    }
}
