package sep3.dto.post;

public class UpdatePostDTO {
    private String title;
    private String content;
    private int postId;
    private int categoryId;

    public UpdatePostDTO(String title, String content, int categoryId, int postId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getPostId() { return postId; }
}
