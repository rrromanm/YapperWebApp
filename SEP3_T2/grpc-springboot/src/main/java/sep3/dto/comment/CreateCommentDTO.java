package sep3.dto.comment;

/**
 * Data transfer object for creating a new comment.
 */
public class CreateCommentDTO {
  private String body;
  private int userId;
  private int postId;

  /**
   * Constructs a new CreateCommentDTO with the provided details for creating a comment.
   *
   * @param body The body content of the comment.
   * @param userId The ID of the user who is creating the comment.
   * @param postId The ID of the post the comment is being created for.
   */
  public CreateCommentDTO(String body, int userId, int postId) {
    this.body = body;
    this.userId = userId;
    this.postId = postId;
  }

  /**
   * Retrieves the body content of the comment.
   *
   * @return The body of the comment.
   */
  public String getBody() {
    return body;
  }

  /**
   * Retrieves the ID of the user who is creating the comment.
   *
   * @return The user ID.
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Retrieves the ID of the post the comment is being created for.
   *
   * @return The post ID.
   */
  public int getPostId() {
    return postId;
  }
}
