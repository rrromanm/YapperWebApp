namespace DTOs.DTOs.Comment;

public class UpdateCommentDTO
{
    public string body { get; set; }
    public int commentId { get; set; }
    
    public UpdateCommentDTO(int commentId, string body)
    {
        this.body = body;
        this.commentId = commentId;
    }
}