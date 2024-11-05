namespace DTOs.User.PostDTOs;

public class UpdatePostDTO
{
    public int PostId { get; set; }
    public string Title { get; set; }
    public string Content { get; set; }
    public int AccountId { get; set; }
    public int CategoryId { get; set; }
    
    public UpdatePostDTO(int postId, string title, string content, int accountId, int categoryId)
    {
        PostId = postId;
        Title = title;
        Content = content;
        AccountId = accountId;
        CategoryId = categoryId;
    }
}