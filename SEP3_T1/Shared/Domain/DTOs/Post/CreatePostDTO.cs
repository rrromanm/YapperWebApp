namespace DTOs.User.PostDTOs;

public class CreatePostDTO(string title, string body, int categoryId, int accountId)
{
    public string Title { get; set; } = title;
    public string Body { get; set; } = body;
    public int CategoryId { get; set; } = categoryId;
    public int AccountId { get; set; } = accountId;
}