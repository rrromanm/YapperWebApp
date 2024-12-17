package sep3.service;

import chat.*;
import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import sep3.dao.ChatDAOInterface;
import sep3.dto.chat.MessageDTO;
import sep3.dto.chat.SendMessageDTO;

import java.util.List;
import com.rabbitmq.client.ConnectionFactory;

public class ChatImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private ChatDAOInterface dao;

    private Gson gson;

    private static final String EXCHANGE_NAME = "chat.exchange";

    public ChatImpl(ChatDAOInterface dao) {
        this.dao = dao;
        this.gson = new Gson();
    }

    /**
     * Sends a message from the sender to the receiver and publishes it to RabbitMQ.
     *
     * @param request The request containing the message details, including the message text, receiver ID, and sender ID.
     * @param responseObserver The stream observer to send the response.
     */
    @Override
    public void sendMessage(SendMessageRequest request, StreamObserver<EmptyMessageResponse> responseObserver) {
        try {
            SendMessageDTO dto = new SendMessageDTO(request.getMessage(), request.getReceiverId(), request.getSenderId());
            dao.sendMessage(dto);

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            try (var connection = factory.newConnection(); var channel = connection.createChannel()) {
                channel.exchangeDeclare(EXCHANGE_NAME, "topic");
                String routingKey = "chat." + request.getReceiverId();
                String message = gson.toJson(dto);

                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
                System.out.println("Message published to RabbitMQ: " + message);
            }

            responseObserver.onNext(EmptyMessageResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Retrieves all messages from the database.
     *
     * @param request The request to retrieve all messages.
     * @param responseObserver The stream observer to send the list of all messages.
     */
    @Override
    public void getAllMessages(EmptyMessageRequest request, StreamObserver<GetAllMessagesResponse> responseObserver) {
        try{
            List<MessageDTO> messages = dao.getAllMessages();
            String string = gson.toJson(messages);
            GetAllMessagesResponse response = GetAllMessagesResponse.newBuilder().setList(string).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Retrieves messages between a specific sender and receiver.
     *
     * @param request The request containing the sender ID and receiver ID.
     * @param responseObserver The stream observer to send the list of messages between the sender and receiver.
     */
    @Override
    public void getMessages(GetMessagesRequest request, StreamObserver<GetMessagesResponse> responseObserver) {
        try {
            List<MessageDTO> messages = dao.getMessages(request.getSenderId(), request.getReceiverId());
            System.out.println("messages get from: " + request.getSenderId() + " to: " + request.getReceiverId());
            System.out.println("messages: " + messages);
            GetMessagesResponse.Builder responseBuilder = GetMessagesResponse.newBuilder();

            for (MessageDTO message : messages) {
                MessageResponse messageResponse = MessageResponse.newBuilder()
                        .setMessageId(message.getMessageId())
                        .setSenderId(message.getSender())
                        .setReceiverId(message.getReceiver())
                        .setMessage(message.getMessage())
                        .setTimestamp(message.getTimestamp())
                        .build();
                responseBuilder.addMessages(messageResponse);
            }

            GetMessagesResponse response = responseBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
