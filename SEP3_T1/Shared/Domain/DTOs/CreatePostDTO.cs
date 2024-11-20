namespace DTOs.User.PostDTOs;

public class CreatePostDTO(string title, string content, int categoryId, int accountId)
{
    public string Title { get; set; } = title;
    public string Content { get; set; } = content;
    public int CategoryId { get; set; } = categoryId;
    public int AccountId { get; set; } = accountId;
}