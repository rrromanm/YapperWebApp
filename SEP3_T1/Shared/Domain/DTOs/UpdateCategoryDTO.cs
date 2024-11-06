namespace DTOs.User;

public class UpdateCategoryDTO
{
    public int id { get; set; }
    public string? name { get; set; }
    
    public UpdateCategoryDTO(int id, string? name)
    {
        this.id = id;
        this.name = name;
    }
}