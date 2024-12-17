package sep3.dto.post;

/**
 * Data transfer object representing a post with details including title, body, like count,
 * comment count, post date, category ID, post ID, and user ID.
 */
public class PostDTO {
    private String title;
    private String body;
    private int likeCount;
    private int commentCount;
    private String postDate;
    private int categoryId;
    private int postId;
    private int userId;

    /**
     * Constructs a new PostDTO with the specified title, body, like count, comment count,
     * post date, category ID, post ID, and user ID.
     *
     * @param title       The title of the post.
     * @param body        The body/content of the post.
     * @param likeCount   The number of likes for the post.
     * @param commentCount The number of comments on the post.
     * @param postDate    The date the post was created.
     * @param categoryId  The ID of the category to which the post belongs.
     * @param postId      The unique ID of the post.
     * @param userId      The ID of the user who created the post.
     */
    public PostDTO(String title, String body, int likeCount, int commentCount, String postDate,
                   int categoryId, int postId, int userId) {
        this.title = title;
        this.body = body;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.postDate = postDate;
        this.categoryId = categoryId;
        this.postId = postId;
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
     * Retrieves the body/content of the post.
     *
     * @return The body of the post.
     */
    public String getBody() {
        return body;
    }

    /**
     * Retrieves the date the post was created.
     *
     * @return The post creation date.
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     * Retrieves the category ID to which the post belongs.
     *
     * @return The category ID.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Retrieves the unique ID of the post.
     *
     * @return The post ID.
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Retrieves the ID of the user who created the post.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the number of likes the post has received.
     *
     * @return The like count.
     */
    public int getLikeCount() {
        return likeCount;
    }

    /**
     * Retrieves the number of comments on the post.
     *
     * @return The comment count.
     */
    public int getCommentCount() {
        return commentCount;
    }
}
