package sep3.dto.comment;

public class UpdateCommentDTO
{
  private String body;
  private int commentId;

  public UpdateCommentDTO(int commentId, String body)
  {
    this.body = body;
    this.commentId = commentId;
  }

  public int getCommentId()
  {
    return commentId;
  }

  public String getBody()
  {
    return body;
  }
}
