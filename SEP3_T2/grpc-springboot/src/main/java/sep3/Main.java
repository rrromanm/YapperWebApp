package sep3;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import sep3.dao.CategoryDAO;
import sep3.dao.ChatDAO;
import sep3.service.*;
import sep3.dao.PostDAO;
import sep3.dao.SMUserDAO;
import sep3.service.CategoryImpl;
import sep3.service.CommentImpl;
import sep3.service.PostImpl;
import sep3.service.SMUserImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
                .addService(new SMUserImpl(SMUserDAO.getInstance()))
                .addService(new CategoryImpl(CategoryDAO.getInstance()))
                .addService(new PostImpl(PostDAO.getInstance()))
                .addService(new CommentImpl())
                .addService(new ChatImpl(ChatDAO.getInstance()))
                .build()
                .start();

        System.out.println("Server started, listening on " + server.getPort());
        server.awaitTermination();
    }
}
