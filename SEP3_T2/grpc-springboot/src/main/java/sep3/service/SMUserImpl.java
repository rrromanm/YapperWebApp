package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import sep3.dao.SMUserDAOInterface;
import sep3.dto.smuser.CreateSMUserDTO;
import sep3.dto.smuser.FollowerDTO;
import sep3.dto.smuser.SMUserDTO;
import socialMediaUser.CreateSMUserRequest;
import socialMediaUser.CreateSMUserResponse;
import socialMediaUser.DeleteSMUserRequest;
import socialMediaUser.FollowUserRequest;
import socialMediaUser.GetAllUsersRequest;
import socialMediaUser.GetAllUsersResponse;
import socialMediaUser.GetFollowersRequest;
import socialMediaUser.GetFollowersResponse;
import socialMediaUser.GetSMUserRequest;
import socialMediaUser.GetThreeRandomUsersRequest;
import socialMediaUser.GetThreeRandomUsersResponse;
import socialMediaUser.GetUsersBySearchRequest;
import socialMediaUser.GetUsersBySearchResponse;
import socialMediaUser.IsFollowingRequest;
import socialMediaUser.IsFollowingResponse;
import socialMediaUser.SMUserEmptyResponse;
import socialMediaUser.SMUserResponse;
import socialMediaUser.SMUserServiceGrpc;
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

    /**
     * Creates a new social media user.
     *
     * @param request The request containing user details (username, email, password, etc.).
     * @param responseObserver The observer to handle the response or error.
     */
    @Override
    public void createUser(CreateSMUserRequest request, StreamObserver<CreateSMUserResponse> responseObserver) {
        try {
            System.out.println("User created with username: " + request.getUsername());
            CreateSMUserDTO dto = new CreateSMUserDTO(
                    request.getUsername(),
                    request.getNickname(),
                    request.getPassword(),
                    request.getEmail());
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

    /**
     * Updates the email of an existing user.
     *
     * @param request The request containing the user ID and the new email.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Updates the nickname of an existing user.
     *
     * @param request The request containing the user ID and the new nickname.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Updates the password of an existing user.
     *
     * @param request The request containing the user ID and the new password.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Deletes an existing user by ID.
     *
     * @param request The request containing the user ID to delete.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Retrieves a social media user by their username.
     *
     * @param request The request containing the username.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Retrieves a social media user by their user ID.
     *
     * @param request The request containing the user ID.
     * @param responseObserver The observer to handle the response or error.
     */
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
                    .setFollowersCount(userDTO.getFollowerCount())
                    .setFollowingCount(userDTO.getFollowingCount())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Retrieves a list of all users.
     *
     * @param request The request to get all users.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Follows another user.
     *
     * @param request The request containing the follower and followed user IDs.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Unfollows a user.
     *
     * @param request The request containing the follower and followed user IDs.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Retrieves the followers of a specific user.
     *
     * @param request The request containing the user ID.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Retrieves the users that a specific user is following.
     *
     * @param request The request containing the user ID.
     * @param responseObserver The observer to handle the response or error.
     */
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


    /**
     * Checks if one user is following another user.
     *
     * @param request The request containing the follower and followed user IDs.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Retrieves three random users based on a user's ID.
     *
     * @param request The request containing the user ID.
     * @param responseObserver The observer to handle the response or error.
     */
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

    /**
     * Retrieves users based on a search text.
     *
     * @param request The request containing the search text.
     * @param responseObserver The observer to handle the response or error.
     */
    @Override
    public void getUsersBySearch(GetUsersBySearchRequest request, StreamObserver<GetUsersBySearchResponse> responseObserver) {
        try {
            ArrayList<FollowerDTO> usersBySearch = dao.getUsersBySearch(request.getSearchText());

            String jsonFollowers = gson.toJson(usersBySearch);

            GetUsersBySearchResponse response = GetUsersBySearchResponse.newBuilder()
                    .setList(jsonFollowers)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}