package sep3.service;

import socialMediaUser.*;
import io.grpc.stub.StreamObserver;
import socialMediaUser.CreateSMUserRequest;
import socialMediaUser.DeleteSMUserRequest;
import socialMediaUser.SMUserEmptyResponse;
import socialMediaUser.UpdateSMUserEmailRequest;
import socialMediaUser.UpdateSMUserNicknameRequest;
import socialMediaUser.UpdateSMUserPasswordRequest;


public class SMUserImpl extends SMUserServiceGrpc.SMUserServiceImplBase {

    @Override
    public void createUser(CreateSMUserRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try{
            // Implement your logic here
            System.out.println("User created with email: " + request.getEmail());
            System.out.println("Username: " + request.getUsername());
            System.out.println("Nickname: " + request.getNickname());
            System.out.println("Password: " + request.getPassword());
            // Complete the gRPC call
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

