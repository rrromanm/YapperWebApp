namespace DTOs.User.PostDTOs;

public class CreatePostDTO
{
    public string Title { get; set; }
    public string Content { get; set; }
    public int AccountId { get; set; }
    public int CategoryId { get; set; }
    
    public CreatePostDTO(string title, string content, int accountId, int categoryId)
    {
        Title = title;
        Content = content;
        AccountId = accountId;
        CategoryId = categoryId;
    }
}