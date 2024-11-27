namespace DTOs.User.PostDTOs;

public class UpdatePostDTO
{
    public int PostId { get; set; }
    public string Title { get; set; }
    public string Body { get; set; }
    public int AccountId { get; set; }
    public int CategoryId { get; set; }
    
    public UpdatePostDTO(int postId, string title, string body, int accountId, int categoryId)
    {
        PostId = postId;
        Title = title;
        Body = body;
        AccountId = accountId;
        CategoryId = categoryId;
    }
}