namespace DTOs.DTOs;

public class CreateCommentDTO
{
    public string body { get; set; }
    public int userId { get; set; }
    public int postId { get; set; }
    
    public CreateCommentDTO(string body,int userId, int postId)
    {
        this.body = body;
        this.userId = userId;
        this.postId = postId;
    }
}