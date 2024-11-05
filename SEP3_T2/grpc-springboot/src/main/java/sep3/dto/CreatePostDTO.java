package sep3.dto;

public class CreatePostDTO {
    private String title;
    private String content;
    private int accountId;
    private int categoryId;

    public CreatePostDTO(String title, String content, int accountId) {
        this.title = title;
        this.content = content;
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
