package sep3.dto.post;

/**
 * Data transfer object for creating a new post.
 */
public class CreatePostDTO {
    private String title;
    private String content;
    private int userId;
    private int categoryId;

    /**
     * Constructs a new CreatePostDTO with the specified title, content, category ID, and user ID.
     *
     * @param title      The title of the post.
     * @param content    The content of the post.
     * @param categoryId The ID of the category to which the post belongs.
     * @param userId     The ID of the user who created the post.
     */
    public CreatePostDTO(String title, String content, int categoryId, int userId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    /**
     * Retrieves the title of the post.
     *
     * @return The title of the post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the content of the post.
     *
     * @return The content of the post.
     */
    public String getContent() {
        return content;
    }

    /**
     * Retrieves the user ID of the user who created the post.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the category ID to which the post belongs.
     *
     * @return The category ID.
     */
    public int getCategoryId() {
        return categoryId;
    }
}
