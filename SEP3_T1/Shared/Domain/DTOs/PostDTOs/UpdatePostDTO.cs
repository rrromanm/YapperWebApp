namespace DTOs.User.PostDTOs;

public class UpdatePostDTO
{
    public int postId { get; set; }
    public string title { get; set; }
    public string content { get; set; }
    public int accountId { get; set; }
    public int categoryId { get; set; }
    
    public UpdatePostDTO(int postId, string title, string content, int accountId, int categoryId)
    {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }
}