namespace DTOs.DTOs.Comment;

public class CommentDTO
{
    public string body { get; set; }
    public string commentDate { get; set; }
    public int likeCount { get; set; }
    public int commentId { get; set; }
    public int userId { get; set; }
    public int postId { get; set; }
}