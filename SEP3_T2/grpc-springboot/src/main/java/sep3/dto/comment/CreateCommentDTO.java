package sep3.dto.comment;

public class CreateCommentDTO
{
  private String body;
  private int userId;
  private int postId;

  public CreateCommentDTO(String body, int userId, int postId)
  {
    this.body = body;
    this.userId = userId;
    this.postId = postId;
  }

  public String getBody()
  {
    return body;
  }

  public int getUserId()
  {
    return userId;
  }

  public int getPostId()
  {
    return postId;
  }
}
