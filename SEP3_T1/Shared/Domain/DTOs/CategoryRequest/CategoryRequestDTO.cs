namespace DTOs.DTOs.CategoryRequest;

public class CategoryRequestDTO
{
    public string Date { get; set; }
    public string categoryName { get; set; }
    public int userId { get; set; }
    public int requestId { get; set;}
    
    public CategoryRequestDTO(string date, string categoryName, int userId, int requestId)
    {
        this.Date = date;
        this.categoryName = categoryName;
        this.userId = userId;
        this.requestId = requestId;
    }
}