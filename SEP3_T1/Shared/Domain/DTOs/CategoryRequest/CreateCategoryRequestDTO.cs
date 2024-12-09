namespace DTOs.DTOs.CategoryRequest;

public class CreateCategoryRequestDTO
{
    public string CategoryName { get; set; }
    public int UserId { get; set; }
    
    public CreateCategoryRequestDTO(string categoryName, int userId)
    {
        this.CategoryName = categoryName;
        this.UserId = userId;
    }
}