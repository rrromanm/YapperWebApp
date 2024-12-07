package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import sep3.dao.SMUserDAOInterface;
import sep3.dto.smuser.CreateSMUserDTO;
import sep3.dto.smuser.FollowerDTO;
import sep3.dto.smuser.SMUserDTO;
import socialMediaUser.*;

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
    public void createUser(CreateSMUserRequest request, StreamObserver<CreateSMUserResponse> responseObserver) {
        try {
            System.out.println("User created with username: " + request.getUsername());
            CreateSMUserDTO dto = new CreateSMUserDTO(request.getUsername(), request.getNickname(), request.getPassword(), request.getEmail());
            int userId = dao.createUser(dto);
            SMUserDTO user = dao.getUserById(userId);
            CreateSMUserResponse response = CreateSMUserResponse.newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setUsername(user.getUsername())
                .setNickname(user.getNickname())
                .setPassword(user.getPassword())
                .setFollowersCount(user.getFollowerCount())
                .setFollowingCount(user.getFollowingCount())
                .build();
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
                    .setFollowersCount(user.getFollowerCount())
                    .setFollowingCount(user.getFollowingCount())
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
                    .setFollowersCount(userDTO.getFollowerCount()) // Assuming SMUserDTO has these fields
                    .setFollowingCount(userDTO.getFollowingCount()) // Assuming SMUserDTO has these fields
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

    @Override
    public void getFollowers(GetFollowersRequest request, StreamObserver<GetFollowersResponse> responseObserver) {
        try {
            ArrayList<FollowerDTO> followers = dao.getFollowers(request.getId());

            String jsonFollowers = gson.toJson(followers);

            GetFollowersResponse response = GetFollowersResponse.newBuilder()
                    .setList(jsonFollowers)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }


    @Override
    public void getFollowing(GetFollowersRequest request, StreamObserver<GetFollowersResponse> responseObserver) {
        try {
            ArrayList<FollowerDTO> following = dao.getFollowing(request.getId());

            String jsonFollowing = gson.toJson(following);

            GetFollowersResponse response = GetFollowersResponse.newBuilder()
                    .setList(jsonFollowing)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void isFollowing(IsFollowingRequest request, StreamObserver<IsFollowingResponse> responseObserver) {
        try {
            boolean isFollowing = dao.isFollowing(request.getFollowerId(), request.getFollowedId());

            IsFollowingResponse response = IsFollowingResponse.newBuilder()
                    .setIsFollowing(isFollowing)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getThreeRandomUsers(GetThreeRandomUsersRequest request, StreamObserver<GetThreeRandomUsersResponse> responseObserver) {
        try {
            ArrayList<FollowerDTO> randomUsers = dao.getThreeRandomUsers(request.getUserId());

            String jsonFollowers = gson.toJson(randomUsers);

            GetThreeRandomUsersResponse response = GetThreeRandomUsersResponse.newBuilder()
                    .setList(jsonFollowers)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}