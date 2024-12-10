package sep3.dto.report;

public class PostReportDTO {
    private int userID;
    private int postID;

    private String timestamp;

    private int reportID;

    public PostReportDTO(int userID, int postID, String timestamp, int reportID) {
        this.userID = userID;
        this.postID = postID;
        this.timestamp = timestamp;
        this.reportID = reportID;
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

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String toString() {
        return "userID=" + userID +
                ", postID=" + postID +
                ", timestamp=" + timestamp +
                ", reportID=" + reportID +
                '}';
    }



}
