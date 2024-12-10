namespace DTOs.Models;

public class CategoryRequest
{
    public string Date { get; set; }
    public  string CategoryName { get; set; }
    public  int UserId { get; set; }
    public  int RequestId { get; set;}
    
    public CategoryRequest(string date, string categoryName, int userId, int requestId)
    {
        this.Date = date;
        this.CategoryName = categoryName;
        this.UserId = userId;
        this.RequestId = requestId;
    }

    public override string ToString()
    {
        return $"Date: {Date}, Category Name: {CategoryName}, User ID: {UserId}, Request ID: {RequestId}";
    }
    protected bool Equals(CategoryRequest other)
    {
        return Date == other.Date && CategoryName == other.CategoryName && UserId == other.UserId && RequestId == other.RequestId;
    }

    public override bool Equals(object obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((CategoryRequest)obj);
    }
    public override int GetHashCode()
    {
        return HashCode.Combine(Date, CategoryName, UserId, RequestId);
    }
}