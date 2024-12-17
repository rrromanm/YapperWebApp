package sep3.dto.comment;

/**
 * Data transfer object for updating an existing comment.
 */
public class UpdateCommentDTO {
  private String body;
  private int commentId;

  /**
   * Constructs a new UpdateCommentDTO with the provided details for updating a comment.
   *
   * @param commentId The ID of the comment being updated.
   * @param body The new body content of the comment.
   */
  public UpdateCommentDTO(int commentId, String body) {
    this.body = body;
    this.commentId = commentId;
  }

  /**
   * Retrieves the ID of the comment being updated.
   *
   * @return The comment ID.
   */
  public int getCommentId() {
    return commentId;
  }

  /**
   * Retrieves the new body content of the comment.
   *
   * @return The new body of the comment.
   */
  public String getBody() {
    return body;
  }
}
