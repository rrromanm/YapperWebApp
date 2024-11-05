package sep3.service;

import io.grpc.stub.StreamObserver;
import yapperPost.CreatePostRequest;
import yapperPost.PostEmptyMessage;
import yapperPost.PostServiceGrpc;

public class PostImpl extends PostServiceGrpc.PostServiceImplBase {

    public void createPost(CreatePostRequest request, StreamObserver<PostEmptyMessage> responseObserver) {
        try{
            System.out.println("User created with email: " + request.getTitle());
            System.out.println("Username: " + request.getContent());
            System.out.println("By: " + request.getAccountId());
            System.out.println("Category ID: " + request.getCategoryId());
            // Complete the gRPC call
            responseObserver.onNext(PostEmptyMessage.newBuilder().build());
            responseObserver.onCompleted();
        }catch (Exception e){
            responseObserver.onError(e);
        }
    }
}
