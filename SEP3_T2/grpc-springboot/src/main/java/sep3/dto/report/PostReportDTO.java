package sep3.dto.report;

public class PostReportDTO {
    private int userID;
    private int postID;

    private String timestamp;

    public PostReportDTO(int userID, int postID, String timestamp) {
        this.userID = userID;
        this.postID = postID;
        this.timestamp = timestamp;
    }

    public int getUserID() {
        return userID;
    }

    public int getPostID() {
        return postID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "userID=" + userID +
                ", postID=" + postID +
                ", timestamp=" + timestamp +
                '}';
    }



}
