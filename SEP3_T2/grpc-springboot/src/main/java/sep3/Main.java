package sep3;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import sep3.dao.*;
import sep3.service.*;
import sep3.service.CategoryImpl;
import sep3.service.CommentImpl;
import sep3.service.PostImpl;
import sep3.service.SMUserImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        startRabbitMQServer();
        Server server = ServerBuilder.forPort(8080)
                .addService(new SMUserImpl(SMUserDAO.getInstance()))
                .addService(new CategoryImpl(CategoryDAO.getInstance()))
                .addService(new PostImpl(PostDAO.getInstance()))
                .addService(new CommentImpl(CommentDAO.getInstance()))
                .addService(new ChatImpl(ChatDAO.getInstance()))
                .addService(new NotificationImpl(NotificationDAO.getInstance()))
                .build()
                .start();

        System.out.println("Server started, listening on " + server.getPort());
        server.awaitTermination();
    }
    private static void startRabbitMQServer() {
        try {
            Process process = new ProcessBuilder("rabbitmq-server").start();
            System.out.println("RabbitMQ server started.");
        } catch (IOException e) {
            System.err.println("Failed to start RabbitMQ server: " + e.getMessage());
        }
    }
}
