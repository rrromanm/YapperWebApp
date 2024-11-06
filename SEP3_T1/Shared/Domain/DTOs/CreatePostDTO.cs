namespace DTOs.User.PostDTOs;

public class CreatePostDTO(string title, string content, int accountId, int categoryId)
{
    public string Title { get; set; } = title;
    public string Content { get; set; } = content;
    public int AccountId { get; set; } = accountId;
    public int CategoryId { get; set; } = categoryId;
}