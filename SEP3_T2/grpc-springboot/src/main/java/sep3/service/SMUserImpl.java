package sep3.service;

import io.grpc.stub.StreamObserver;
import sep3.dao.SMUserDAO;
import sep3.dto.smuser.CreateSMUserDTO;
import sep3.dto.smuser.UpdateSMUserDTO;
import socialMediaUser.*;

public class SMUserImpl extends SMUserServiceGrpc.SMUserServiceImplBase {

    private SMUserDAO dao;

    public SMUserImpl(SMUserDAO dao) {
        this.dao = dao;
    }

    @Override
    public void createUser(CreateSMUserRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try {
            System.out.println("User created with username: " + request.getUsername());
            CreateSMUserDTO dto = new CreateSMUserDTO(request.getUsername(), request.getNickname(), request.getPassword(), request.getEmail());
            dao.createUser(dto);
            SMUserEmptyResponse response = SMUserEmptyResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateSMUser(UpdateSMUserRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try {
            //todo implement update user
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteUser(DeleteSMUserRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try {
            System.out.println("User deleted with ID: " + request.getId());
            dao.deleteSMUser(request.getId());
            SMUserEmptyResponse response = SMUserEmptyResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}