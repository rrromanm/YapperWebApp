package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import sep3.dao.PostDAO;
import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.PostDTO;
import sep3.dto.post.UpdatePostDTO;
import yapperPost.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class PostImpl extends PostServiceGrpc.PostServiceImplBase {
    private PostDAO dao;
    private final Gson gson;

    public PostImpl(PostDAO dao)
    {
        this.dao = dao;
        this.gson = new Gson();
    }

    @Override
    public void createPost(CreatePostRequest request, StreamObserver<PostEmptyMessage> responseObserver) {
        try
        {
            CreatePostDTO dto = new CreatePostDTO(request.getTitle(), request.getBody(), request.getCategoryId(), request.getUserId());
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
            UpdatePostDTO dto = new UpdatePostDTO(request.getTitle(), request.getBody(), request.getCategoryId(), request.getPostId());
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

            responseObserver.onNext(PostEmptyMessage.newBuilder().build());
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getPost(GetPostRequest request, StreamObserver<GetPostResponse> responseObserver) {
        try
        {
            PostDTO post = dao.getPost(request.getPostId());

            responseObserver.onNext(GetPostResponse.newBuilder()
                    .setTitle(post.getTitle())
                    .setBody(post.getBody())
                    .setLikeCount(post.getLikeCount())
                    .setCommentCount(post.getCommentCount())
                    .setDate(post.getPostDate())
                    .setCategoryId(post.getCategoryId())
                    .setPostId(post.getPostId())
                    .setUserId(post.getUserId())
                    .build());
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllPosts(EmptyGetAllPostsRequest request, StreamObserver<GetAllPostsResponse> responseObserver) {
        try
        {
            ArrayList<PostDTO> posts = dao.getAllPosts();

            String string = gson.toJson(posts);
            GetAllPostsResponse response = GetAllPostsResponse.newBuilder().setList(string).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllFollowingPosts(GetAllFollowingPostsRequest request, StreamObserver<GetAllFollowingPostsResponse> responseObserver) {
        try
        {
            ArrayList<PostDTO> posts = dao.getAllFollowingPosts(request.getUserId());

            String string = gson.toJson(posts);
            GetAllFollowingPostsResponse response = GetAllFollowingPostsResponse.newBuilder().setList(string).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllPostsById(GetAllPostsByIdRequest request, StreamObserver<GetAllPostsByIdResponse> responseObserver) {
        try
        {
            ArrayList<PostDTO> posts = dao.getAllPostsById(request.getUserId());

            String string = gson.toJson(posts);
            GetAllPostsByIdResponse response = GetAllPostsByIdResponse.newBuilder().setList(string).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllPostsByCategory(GetAllPostsByCategoryRequest request, StreamObserver<GetAllPostsByCategoryResponse> responseObserver) {
        try
        {
            ArrayList<PostDTO> posts = dao.getAllPostsByCategory(request.getCategoryId());

            String string = gson.toJson(posts);
            GetAllPostsByCategoryResponse response = GetAllPostsByCategoryResponse.newBuilder().setList(string).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllLikedPosts(GetAllLikedPostsRequest request, StreamObserver<GetAllLikedPostsResponse> responseObserver) {
        try {
            ArrayList<PostDTO> posts = dao.getAllLikedPosts(request.getUserId());

            String string = gson.toJson(posts);
            GetAllLikedPostsResponse response = GetAllLikedPostsResponse.newBuilder().setList(string).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void likePost(LikePostRequest request, StreamObserver<PostEmptyMessage> responseObserver) {
        try
        {
            dao.likePost(request.getUserId(), request.getPostId());

            PostEmptyMessage response = PostEmptyMessage.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }

    @Override
    public void unlikePost(UnlikePostRequest request, StreamObserver<PostEmptyMessage> responseObserver) {
        try
        {
            dao.unlikePost(request.getUserId(), request.getPostId());

            PostEmptyMessage response = PostEmptyMessage.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            responseObserver.onError(e);
        }
    }
}