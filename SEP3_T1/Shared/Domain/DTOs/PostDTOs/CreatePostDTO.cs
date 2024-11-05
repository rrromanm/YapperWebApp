namespace DTOs.User.PostDTOs;

public class CreatePostDTO
{
    public string title { get; set; }
    public string content { get; set; }
    public int accountId { get; set; }
    public int categoryId { get; set; }
    
    public CreatePostDTO(string title, string content, int accountId, int categoryId)
    {
        this.title = title;
        this.content = content;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }
}