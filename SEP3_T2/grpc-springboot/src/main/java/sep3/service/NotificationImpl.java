package sep3.service;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import notification.*;
import sep3.dao.NotificationDAOInterface;
import sep3.dto.notification.NotificationDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NotificationImpl extends NotificationServiceGrpc.NotificationServiceImplBase {

    private NotificationDAOInterface dao;
    private final Gson gson;

    public NotificationImpl(NotificationDAOInterface dao) {
        this.dao = dao;
        this.gson = new Gson();
    }

    @Override
    public void sendNotification(NotificationRequest request, StreamObserver<EmptyResponse> responseObserver) {

            try{

                NotificationDTO notification = new NotificationDTO();
                notification.setNotificationMessage(request.getNotificationMessage());
                notification.setUserId(request.getUserId());
                notification.setTimestamp(String.valueOf(System.currentTimeMillis()));

                dao.sendNotification(request.getUserId(), request.getNotificationMessage());
            }catch (Exception e){
                responseObserver.onError(e);
            }
            responseObserver.onNext(EmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
    }


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

