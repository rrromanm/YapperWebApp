package sep3.service;

import comment.CommentEmptyResponse;
import comment.CommentServiceGrpc;
import comment.CreateCommentRequest;
import comment.UpdateCommentRequest;
import io.grpc.stub.StreamObserver;
import sep3.dao.CommentDAO;

public class CommentImpl extends CommentServiceGrpc.CommentServiceImplBase
{
  private CommentDAO dao;

  public CommentImpl(CommentDAO dao)
  {
    this.dao = dao;
  }
  @Override public void createComment(CreateCommentRequest request, StreamObserver<CommentEmptyResponse> responseObserver)
  {
    try
    {
      System.out.println("Comment created with content: " + request.getBody());
      System.out.println("By: " + request.getUserId());
      System.out.println("Post ID: " + request.getPostId());

      // Complete the gRPC call
      responseObserver.onNext(CommentEmptyResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override public void updateComment(UpdateCommentRequest request,
      StreamObserver<CommentEmptyResponse> responseObserver)
  {
    try
    {
      System.out.println("Comment updated with content: " + request.getBody());
      System.out.println("Comment ID: " + request.getCommentId());

      // Complete the gRPC call
      responseObserver.onNext(CommentEmptyResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }
}
