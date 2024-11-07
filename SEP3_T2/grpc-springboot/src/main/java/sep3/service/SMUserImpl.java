package sep3.service;

import com.google.gson.Gson;
import postCategory.EmptyCategoryResponse;
import sep3.dao.CategoryDAOInterface;
import sep3.dao.SMUserDAO;
import sep3.dto.smuser.CreateSMUserDTO;
import socialMediaUser.*;
import io.grpc.stub.StreamObserver;
import socialMediaUser.CreateSMUserRequest;
import socialMediaUser.DeleteSMUserRequest;
import socialMediaUser.SMUserEmptyResponse;
import socialMediaUser.UpdateSMUserEmailRequest;
import socialMediaUser.UpdateSMUserNicknameRequest;
import socialMediaUser.UpdateSMUserPasswordRequest;


public class SMUserImpl extends SMUserServiceGrpc.SMUserServiceImplBase {

    private SMUserDAO dao;
    private final Gson gson;

    public SMUserImpl(SMUserDAO dao) {
        this.dao = dao;
        this.gson = new Gson();
    }
    @Override
    public void createUser(CreateSMUserRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try{
            System.out.println("User created with username: " + request.getUsername());
            CreateSMUserDTO dto = new CreateSMUserDTO(request.getUsername(), request.getNickname(), request.getPassword(),  request.getEmail());
            dao.createUser(dto);
            SMUserEmptyResponse response = SMUserEmptyResponse.newBuilder().build();
            responseObserver.onNext(SMUserEmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch (Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateEmail(UpdateSMUserEmailRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try{
            // Implement your logic here

            // Complete the gRPC call
            responseObserver.onNext(SMUserEmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch (Exception e){
            // Handle the exception
        }
    }

    @Override
    public void updateNickname(UpdateSMUserNicknameRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try{
            // Implement your logic here
            // Complete the gRPC call
            responseObserver.onNext(SMUserEmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch (Exception e){
            // Handle the exception
        }
    }

    @Override
    public void deleteUser(DeleteSMUserRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try{
            // Implement your logic here
            // Complete the gRPC call
            responseObserver.onNext(SMUserEmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch (Exception e){
            // Handle the exception
        }
    }

    @Override
    public void updatePassword(UpdateSMUserPasswordRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try{
            // Implement your logic here
            // Complete the gRPC call
            responseObserver.onNext(SMUserEmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch (Exception e){
            // Handle the exception
        }
    }


}

