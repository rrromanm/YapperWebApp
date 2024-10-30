package sep3.service;

import io.grpc.stub.StreamObserver;
import sep3.proto.User;
import sep3.proto.UserServiceGrpc;
import sep3.proto.User.CreateUserResponse;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void createUser(User.CreateUserRequest request, StreamObserver<User.CreateUserResponse> responseObserver) {
        User.SocialMediaUser user = request.getUser();

        // Print user information to the console
        System.out.println("User Info:");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());

        // Create response
        User.CreateUserResponse response = User.CreateUserResponse.newBuilder()
                .setUser(user)
                .build();

        // Send response
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}