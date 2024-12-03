package sep3.service;

import chat.*;
import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import sep3.dao.ChatDAOInterface;
import sep3.dto.MessageDTO;
import sep3.dto.SendMessageDTO;

import java.util.ArrayList;
import java.util.List;

public class ChatImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private ChatDAOInterface dao;

    private Gson gson;

    public ChatImpl(ChatDAOInterface dao) {
        this.dao = dao;
        this.gson = new Gson();
    }

    @Override
    public void sendMessage(SendMessageRequest request, StreamObserver<EmptyMessageResponse> responseObserver) {
        try {
            System.out.println("Message sent from: " + request.getSenderId() + " to: " + request.getReceiverId());
            SendMessageDTO dto = new SendMessageDTO(request.getMessage(), request.getReceiverId(), request.getSenderId());
            dao.sendMessage(dto);
            responseObserver.onNext(EmptyMessageResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

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
