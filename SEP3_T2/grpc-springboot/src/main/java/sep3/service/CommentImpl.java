package sep3.service;

import com.google.gson.Gson;
import comment.*;
import io.grpc.stub.StreamObserver;
import sep3.dao.CommentDAO;
import sep3.dto.comment.CommentDTO;
import sep3.dto.comment.CreateCommentDTO;
import sep3.dto.comment.UpdateCommentDTO;

import java.util.ArrayList;

public class CommentImpl extends CommentServiceGrpc.CommentServiceImplBase
{
  private CommentDAO dao;
  private final Gson gson;

  public CommentImpl(CommentDAO dao)
  {
    this.dao = dao;
    this.gson = new Gson();
  }
  @Override
  public void createComment(CreateCommentRequest request, StreamObserver<CommentEmptyResponse> responseObserver)
  {
    try
    {
      CreateCommentDTO dto = new CreateCommentDTO(request.getBody(), request.getUserId(), request.getPostId());
      dao.createComment(dto);

      responseObserver.onNext(CommentEmptyResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override
  public void updateComment(UpdateCommentRequest request,
      StreamObserver<CommentEmptyResponse> responseObserver)
  {
    try
    {
      UpdateCommentDTO dto = new UpdateCommentDTO(request.getCommentId(), request.getBody());
      dao.updateComment(dto);

      responseObserver.onNext(CommentEmptyResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override
  public void deleteComment(DeleteCommentRequest request, StreamObserver<CommentEmptyResponse> responseObserver)
  {
    try
    {
      dao.deleteComment(request.getCommentId());

      responseObserver.onNext(CommentEmptyResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override
  public void getComment(GetCommentRequest request, StreamObserver<GetCommentResponse> responseObserver)
  {
    try{
      CommentDTO comment = dao.getComment(request.getCommentId());

      responseObserver.onNext(GetCommentResponse.newBuilder()
          .setCommentId(comment.getCommentId())
          .setBody(comment.getBody())
          .setUserId(comment.getUserId())
          .setPostId(comment.getPostId())
          .setCommentDate(comment.getCommentDate())
          .setLikeCount(comment.getLikeCount())
          .build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override
  public void getAllComments(EmptyGetAllCommentsRequest request, StreamObserver<GetAllCommentsResponse> responseObserver)
  {
    try{
      ArrayList<CommentDTO> comments = dao.getAllComments();

      String string = gson.toJson(comments);
      GetAllCommentsResponse response = GetAllCommentsResponse.newBuilder().setList(string).build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override
  public void getCommentsByPost(GetCommentsByPostRequest request, StreamObserver<GetCommentsByPostResponse> responseObserver)
  {
    try {
      ArrayList<CommentDTO> comments = dao.getCommentsByPostId(request.getPostId());
      String string = gson.toJson(comments);
      System.out.println("Comments fetched for postId: " + request.getPostId() + " -> " + gson.toJson(comments));
      GetCommentsByPostResponse response = GetCommentsByPostResponse.newBuilder().setList(string).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(e);
    }
  }

  @Override
  public void getCommentsByUser(GetCommentsByUserRequest request, StreamObserver<GetCommentsByUserResponse> responseObserver)
  {
    try{
      ArrayList<CommentDTO> comments = dao.getCommentsByUserId(request.getUserId());

      String string = gson.toJson(comments);
      GetCommentsByUserResponse response = GetCommentsByUserResponse.newBuilder().setList(string).build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }
  @Override
  public void likeComment(LikeCommentRequest request, StreamObserver<CommentEmptyResponse> responseObserver)
  {
    try
    {
      CommentDTO comment = dao.getComment(request.getCommentId());
      String creatorId = "" + comment.getUserId();


      dao.likeComment(request.getCommentId(), request.getUserId());
      responseObserver.onNext(CommentEmptyResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }
  @Override
  public void unlikeComment(UnlikeCommentRequest request, StreamObserver<CommentEmptyResponse> responseObserver)
  {
    try
    {
      dao.unlikeComment(request.getCommentId(), request.getUserId());

      responseObserver.onNext(CommentEmptyResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override public void getAllLikedComments(GetAllLikedCommentsRequest request, StreamObserver<GetAllLikedCommentsResponse> responseObserver)
  {
    try
    {
      ArrayList<CommentDTO> comments = dao.getLikedCommentsByUserId(request.getUserId());

      String string = gson.toJson(comments);
      GetAllLikedCommentsResponse response = GetAllLikedCommentsResponse.newBuilder().setList(string).build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }
}
