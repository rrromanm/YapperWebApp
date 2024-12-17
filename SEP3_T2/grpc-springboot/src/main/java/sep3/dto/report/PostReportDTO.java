package sep3.dto.report;

/**
 * Data transfer object representing a report on a post, containing information
 * about the user who reported it, the post ID, the timestamp of the report,
 * and the report ID.
 */
public class PostReportDTO {
    private int userID;
    private int postID;
    private String timestamp;
    private int reportID;

    /**
     * Constructs a new PostReportDTO with the specified user ID, post ID, timestamp,
     * and report ID.
     *
     * @param userID    The ID of the user who reported the post.
     * @param postID    The ID of the post that was reported.
     * @param timestamp The timestamp when the report was created.
     * @param reportID  The unique ID of the report.
     */
    public PostReportDTO(int userID, int postID, String timestamp, int reportID) {
        this.userID = userID;
        this.postID = postID;
        this.timestamp = timestamp;
        this.reportID = reportID;
    }

    /**
     * Retrieves the ID of the user who reported the post.
     *
     * @return The user ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Retrieves the ID of the reported post.
     *
     * @return The post ID.
     */
    public int getPostID() {
        return postID;
    }

    /**
     * Retrieves the timestamp of when the report was created.
     *
     * @return The timestamp of the report.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the ID of the user who reported the post.
     *
     * @param userID The user ID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Sets the ID of the reported post.
     *
     * @param postID The post ID to set.
     */
    public void setPostID(int postID) {
        this.postID = postID;
    }

    /**
     * Sets the timestamp of the report.
     *
     * @param timestamp The timestamp to set.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the unique ID of the report.
     *
     * @return The report ID.
     */
    public int getReportID() {
        return reportID;
    }

    /**
     * Sets the unique ID of the report.
     *
     * @param reportID The report ID to set.
     */
    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    /**
     * Returns a string representation of the PostReportDTO object.
     *
     * @return A string representing the PostReportDTO object.
     */
    @Override
    public String toString() {
        return "userID=" + userID +
                ", postID=" + postID +
                ", timestamp=" + timestamp +
                ", reportID=" + reportID +
                '}';
    }
}
