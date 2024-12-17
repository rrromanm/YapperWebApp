package sep3.dto.post;

/**
 * Data transfer object representing a post to be updated, including title, content,
 * category ID, and post ID.
 */
public class UpdatePostDTO {
    private String title;
    private String content;
    private int postId;
    private int categoryId;

    /**
     * Constructs a new UpdatePostDTO with the specified title, content, category ID, and post ID.
     *
     * @param title      The new title for the post.
     * @param content    The new content/body of the post.
     * @param categoryId The ID of the category to which the post belongs.
     * @param postId     The unique ID of the post to be updated.
     */
    public UpdatePostDTO(String title, String content, int categoryId, int postId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.postId = postId;
    }

    /**
     * Retrieves the new title for the post.
     *
     * @return The new title of the post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the new content/body of the post.
     *
     * @return The new content of the post.
     */
    public String getContent() {
        return content;
    }

    /**
     * Retrieves the category ID of the post.
     *
     * @return The category ID.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Retrieves the unique ID of the post to be updated.
     *
     * @return The post ID.
     */
    public int getPostId() {
        return postId;
    }
}
