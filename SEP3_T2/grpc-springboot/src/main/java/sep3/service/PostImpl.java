package sep3.service;

import io.grpc.stub.StreamObserver;
import sep3.dao.PostDAO;
import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.UpdatePostDTO;
import yapperPost.*;

import java.sql.SQLException;

public class PostImpl extends PostServiceGrpc.PostServiceImplBase {
    private PostDAO dao;

    public PostImpl(PostDAO dao)
    {
        this.dao = dao;
    }

    @Override
    public void createPost(CreatePostRequest request, StreamObserver<PostEmptyMessage> responseObserver) {
        try
        {
            CreatePostDTO dto = new CreatePostDTO(request.getTitle(), request.getContent(), request.getCategoryId(), request.getCategoryId());
            dao.createPost(dto);

            responseObserver.onNext(PostEmptyMessage.newBuilder().build());
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updatePost(UpdatePostRequest request, StreamObserver<PostEmptyMessage> responseObserver) {
        try
        {
            UpdatePostDTO dto = new UpdatePostDTO(request.getTitle(), request.getContent(), request.getCategoryId(), request.getPostId());
            dao.updatePost(dto);

            responseObserver.onNext(PostEmptyMessage.newBuilder().build());
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deletePost(DeletePostRequest request, StreamObserver<PostEmptyMessage> responseObserver) {
        try
        {
            dao.deletePost(request.getPostId());
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }
}
