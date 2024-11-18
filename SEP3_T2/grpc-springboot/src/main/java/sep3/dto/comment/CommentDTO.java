package sep3.dto.comment;

public class CommentDTO {
    private String body;
    private String commentDate;
    private int likeCount;
    private int commentId;
    private int userId;
    private int postId;

    public CommentDTO(String body, String commentDate, int likeCount, int commentId, int userId, int postId) {
        this.body = body;
        this.commentDate = commentDate;
        this.likeCount = likeCount;
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
    }

    public String getBody() {
        return body;
    }
    public String getCommentDate() {
        return commentDate;
    }
    public int getLikeCount() {
        return likeCount;
    }
    public int getCommentId() {
        return commentId;
    }
    public int getUserId() {
        return userId;
    }
    public int getPostId() {
        return postId;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
}
