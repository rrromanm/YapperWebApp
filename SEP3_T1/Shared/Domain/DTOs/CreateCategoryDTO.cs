namespace DTOs.User;

public class CreateCategoryDTO
{
    public string Name { get; set; }
    public int addedBy { get; set; }
    
    public CreateCategoryDTO(string name, int addedBy)
    {
        this.Name = name;
        this.addedBy = addedBy;
    }
}