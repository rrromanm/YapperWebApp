namespace DTOs.Models;

public class Post
{
    public int Id { get; set; }
    public string Title { get; set; }
    public string Content { get; set; }
    public int UserId { get; set; }
    public int CategoryId { get; set; }
    
    public Post(int id, string title, string content, int userId, int categoryId)
    {
        this.Id = id;
        this.Title = title;
        this.Content = content;
        this.UserId = userId;
        this.CategoryId = categoryId;
    }
}