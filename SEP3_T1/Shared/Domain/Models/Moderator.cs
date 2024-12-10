namespace DTOs.Models;

public class Moderator
{
    public int Id { get; set; }
    public string Username { get; set; }
    public string Password { get; set; }
    public string Role { get; set; } = "Moderator";
    
    public Moderator()
    {
        
    }
    
    public Moderator(string username, string password)
    {
        Username = username;
        Password = password;
    }

    public override string ToString()
    {
        return $"Username: {Username}, Password: {Password}";
    }

    protected bool Equals(Moderator other)
    {
        return Username == other.Username && Password == other.Password;
    }

    public override bool Equals(object obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((Moderator) obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Username, Password);
    }
}