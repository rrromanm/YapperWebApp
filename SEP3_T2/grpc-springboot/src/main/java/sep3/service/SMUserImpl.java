package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import sep3.dao.SMUserDAOInterface;
import sep3.dto.smuser.CreateSMUserDTO;
import sep3.dto.smuser.SMUserDTO;
import socialMediaUser.*;

import java.util.ArrayList;

public class SMUserImpl extends SMUserServiceGrpc.SMUserServiceImplBase {

    private SMUserDAOInterface dao;
    private final Gson gson;

    public SMUserImpl(SMUserDAOInterface dao) {
        this.dao = dao;
        this.gson = new Gson();
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

    @Override
    public void getByUserNameAsync(GetSMUserRequest request, StreamObserver<SMUserResponse> responseObserver) {
        try {
            SMUserDTO user = dao.getUserByUsername(request.getUsername());
            SMUserResponse response = SMUserResponse.newBuilder()
                    .setId(user.getId())
                    .setEmail(user.getEmail())
                    .setUsername(user.getUsername())
                    .setNickname(user.getNickname())
                    .setPassword(user.getPassword())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllSMUsers(GetAllUsersRequest request, StreamObserver<GetAllUsersResponse> responseObserver) {
        try
        {
            ArrayList<SMUserDTO> users = dao.getAllUsers();
            String string = gson.toJson(users);
            GetAllUsersResponse response = GetAllUsersResponse.newBuilder().setList(string).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }
}