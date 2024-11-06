namespace DTOs.User;

public class CategoryDTO
{
    public int Id { get; set; }
    public string Name { get; set; }
    public int addedBy { get; set; }
    
    public CategoryDTO(int id, string name, int addedBy)
    {
        this.Id = id;
        this.Name = name;
        this.addedBy = addedBy;
    }
}