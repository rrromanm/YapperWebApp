namespace DTOs.DTOs.Comment;

public class CommentDTO
{
    public string body { get; set; }
    public string commentDate { get; set; }
    public int likeCount { get; set; }
    public int commentId { get; set; }
    public int userId { get; set; }
    public int postId { get; set; }
    
    public CommentDTO(string body, string commentDate, int likeCount, int commentId, int userId, int postId)
    {
        this.body = body;
        this.commentDate = commentDate;
        this.likeCount = likeCount;
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
    }
}