package sep3.service;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import notification.*;
import sep3.dao.NotificationDAOInterface;
import sep3.dto.notification.NotificationDTO;

import java.sql.SQLException;
import java.util.List;

public class NotificationImpl extends NotificationServiceGrpc.NotificationServiceImplBase {

    private NotificationDAOInterface dao;
    private final Gson gson;

    private static final String EXCHANGE_NAME = "notifications.exchange";

    public NotificationImpl(NotificationDAOInterface dao) {
        this.dao = dao;
        this.gson = new Gson();
    }

    /**
     * Sends a notification to the specified user and publishes it to RabbitMQ.
     *
     * @param request The request containing the user ID and the notification message.
     * @param responseObserver The stream observer to send the response back.
     */
    @Override
    public void sendNotification(NotificationRequest request, StreamObserver<EmptyResponse> responseObserver) {

            try{

                NotificationDTO notification = new NotificationDTO();
                notification.setNotificationMessage(request.getNotificationMessage());
                notification.setUserId(request.getUserId());
                notification.setTimestamp(String.valueOf(System.currentTimeMillis()));

                dao.sendNotification(request.getUserId(), request.getNotificationMessage());

                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");

                try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
                    channel.exchangeDeclare(EXCHANGE_NAME, "topic");
                    String routingKey = "notifications." + request.getUserId();
                    String message = String.format("{\"UserId\": \"%s\", \"NotificationMessage\": \"%s\", \"Timestamp\": \"%s\"}",
                            request.getUserId(),
                            request.getNotificationMessage(),
                            "");

                    channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
                    System.out.println("Notification published to RabbitMQ: " + message);

                    responseObserver.onNext(EmptyResponse.newBuilder().build());
                    responseObserver.onCompleted();
                }
                catch (Exception e){
                    responseObserver.onError(e);
                }
            }catch (Exception e){
                responseObserver.onError(e);
            }

    }

    /**
     * Retrieves the notifications for a specified user.
     *
     * @param request The request containing the user ID for which notifications are to be fetched.
     * @param responseObserver The stream observer to send the notifications response back.
     */
    @Override
    public void getNotifications(GetNotificationRequest request, StreamObserver<GetNotificationResponse> responseObserver) {
        try {
            List<NotificationDTO> notifications = dao.getNotifications(request.getUserId());
            String json = gson.toJson(notifications);
            GetNotificationResponse response = GetNotificationResponse.newBuilder().setList(json).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }
}

