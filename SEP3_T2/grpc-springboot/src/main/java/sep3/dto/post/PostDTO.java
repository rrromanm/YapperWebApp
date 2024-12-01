package sep3.dto.post;

import java.util.Date;

public class PostDTO
{
    private String title;
    private String body;
    private int likeCount;
    private int commentCount;
    private String postDate;
    private int categoryId;
    private int postId;
    private int userId;

    public PostDTO(String title, String body, int likeCount, int commentCount, String postDate ,int categoryId, int postId, int userId) {
        this.title = title;
        this.body = body;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.postDate = postDate;
        this.categoryId = categoryId;
        this.postId = postId;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getPostDate() {
        return postDate; }

    public int getCategoryId() {
        return categoryId;
    }

    public int getPostId() {
        return postId; }

    public int getUserId() {
        return userId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
}
