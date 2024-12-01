package sep3.dto.post;

public class CreatePostDTO {
    private String title;
    private String content;
    private int userId;
    private int categoryId;

    public CreatePostDTO(String title, String content, int categoryId, int userId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
