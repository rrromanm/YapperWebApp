package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import sep3.dao.SMUserDAOInterface;
import sep3.dto.smuser.CreateSMUserDTO;
import sep3.dto.smuser.SMUserDTO;
import socialMediaUser.*;
import socialMediaUser.CreateSMUserRequest;
import socialMediaUser.DeleteSMUserRequest;
import socialMediaUser.FollowUserRequest;
import socialMediaUser.GetAllUsersRequest;
import socialMediaUser.GetAllUsersResponse;
import socialMediaUser.GetSMUserRequest;
import socialMediaUser.SMUserEmptyResponse;
import socialMediaUser.SMUserResponse;
import socialMediaUser.UnfollowUserRequest;
import socialMediaUser.UpdateSMUserEmailRequest;
import socialMediaUser.UpdateSMUserNicknameRequest;
import socialMediaUser.UpdateSMUserPasswordRequest;

import java.sql.SQLException;
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
    public void updateEmail(UpdateSMUserEmailRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try {
            dao.updateEmail(request.getId(), request.getEmail());
            SMUserEmptyResponse response = SMUserEmptyResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (SQLException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateNickname(UpdateSMUserNicknameRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try {
            dao.updateNickname(request.getId(), request.getNickname());
            SMUserEmptyResponse response = SMUserEmptyResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (SQLException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updatePassword(UpdateSMUserPasswordRequest request, StreamObserver<SMUserEmptyResponse> responseObserver) {
        try {
            dao.updatePassword(request.getId(), request.getPassword());
            SMUserEmptyResponse response = SMUserEmptyResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (SQLException e) {
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
    public void getByUserName(GetSMUserRequest request, StreamObserver<SMUserResponse> responseObserver) {
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
    public void getByID(GetSMUserRequest request, StreamObserver<SMUserResponse> responseObserver) {
        try {
            SMUserDTO userDTO = dao.getUserById(request.getId());
            SMUserResponse response = SMUserResponse.newBuilder()
                .setId(userDTO.getId())
                .setUsername(userDTO.getUsername())
                .setNickname(userDTO.getNickname())
                .setPassword(userDTO.getPassword())
                .setEmail(userDTO.getEmail())
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
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

    @Override
    public void followUser(FollowUserRequest request, StreamObserver<SMUserEmptyResponse> responseObserver)
    {
        try{
            dao.followUser(request.getFollowerId(), request.getFollowedId());
            SMUserEmptyResponse response = SMUserEmptyResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }

    }

    @Override
    public void unfollowUser(UnfollowUserRequest request, StreamObserver<SMUserEmptyResponse> responseObserver)
    {
        try{
            dao.unfollowUser(request.getFollowerId(), request.getFollowedId());
            SMUserEmptyResponse response = SMUserEmptyResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }
}