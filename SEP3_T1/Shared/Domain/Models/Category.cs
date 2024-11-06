namespace DTOs.Models;

public class Category
{
    public int Id { get; set; }
    public string Name { get; set; }
    
    public int AddedBy { get; set; }
    
    public Category(int id, string name, int addedBy)
    {
        Id = id;
        Name = name;
        AddedBy = addedBy;
    }
    
    public void toString()
    {
        Console.WriteLine("Category: " + Name + " with id: " + Id + " added by: " + AddedBy);
    }
    
    protected bool Equals(Category other)
    {
        return Id == other.Id && Name == other.Name && AddedBy == other.AddedBy;
    }
    
    public override bool Equals(object obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((Category) obj);
    }
    
    public override int GetHashCode()
    {
        return HashCode.Combine(Id, Name, AddedBy);
    }
}