namespace DTOs.DTOs.Post;

public class PostDTO
{
    public string Title { get; set; }
    public string Body { get; set; }
    public int LikeCount { get; set; }
    public int CommentCount { get; set; }
    public string Date { get; set; }
    public int CategoryId { get; set; }
    public int PostId { get; set; }
    public int UserId { get; set; }
}