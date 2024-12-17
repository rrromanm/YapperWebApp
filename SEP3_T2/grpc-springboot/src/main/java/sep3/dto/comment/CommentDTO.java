package sep3.dto.comment;

/**
 * Data transfer object representing a comment on a post.
 */
public class CommentDTO {
    private String body;
    private String commentDate;
    private int likeCount;
    private int commentId;
    private int userId;
    private int postId;

    /**
     * Constructs a new CommentDTO with the provided comment details.
     *
     * @param body The body content of the comment.
     * @param commentDate The date the comment was posted.
     * @param likeCount The number of likes the comment has received.
     * @param commentId The unique ID of the comment.
     * @param userId The ID of the user who posted the comment.
     * @param postId The ID of the post the comment belongs to.
     */
    public CommentDTO(String body, String commentDate, int likeCount, int commentId, int userId, int postId) {
        this.body = body;
        this.commentDate = commentDate;
        this.likeCount = likeCount;
        this.commentId = commentId;
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
     * Retrieves the date the comment was posted.
     *
     * @return The date the comment was posted.
     */
    public String getCommentDate() {
        return commentDate;
    }

    /**
     * Retrieves the number of likes the comment has received.
     *
     * @return The like count of the comment.
     */
    public int getLikeCount() {
        return likeCount;
    }

    /**
     * Retrieves the unique ID of the comment.
     *
     * @return The comment's ID.
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Retrieves the ID of the user who posted the comment.
     *
     * @return The user's ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the ID of the post the comment belongs to.
     *
     * @return The post's ID.
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Sets the body content of the comment.
     *
     * @param body The new body content of the comment.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Sets the date the comment was posted.
     *
     * @param commentDate The new date the comment was posted.
     */
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * Sets the number of likes the comment has received.
     *
     * @param likeCount The new like count of the comment.
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * Sets the unique ID of the comment.
     *
     * @param commentId The new comment ID.
     */
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    /**
     * Sets the ID of the user who posted the comment.
     *
     * @param userId The new user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the ID of the post the comment belongs to.
     *
     * @param postId The new post ID.
     */
    public void setPostId(int postId) {
        this.postId = postId;
    }
}
