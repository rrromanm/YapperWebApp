package sep3.dto.post;

import java.util.Date;

public class PostDTO
{
    private String title;
    private String content;
    private int postId;
    private Date postDate;
    private int categoryId;

    public PostDTO(String title, String content, Date postDate ,int categoryId, int postId) {
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.categoryId = categoryId;
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate; }

    public int getCategoryId() {
        return categoryId;
    }

    public int getPostId() {
        return postId; }
}
