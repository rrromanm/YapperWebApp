namespace DTOs.Models;

public class SocialMediaUser : User
{
    public string Email { get; set; }
    public string Nickname { get; set; }
    
    public SocialMediaUser(string username, string password, string email, string nickname)
    {
        Username = username;
        Password = password;
        Email = email;
        Nickname = nickname;
    }
    
    public override string ToString()
    {
        return $"Username: {Username}, Password: {Password}, Email: {Email} Nickname: {Nickname}";
    }
    
    protected bool Equals(SocialMediaUser other)
    {
        return Username == other.Username && Password == other.Password && Email == other.Email && Nickname == other.Nickname;
    }
    
    public override bool Equals(object obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((SocialMediaUser) obj);
    }
    
    public override int GetHashCode()
    {
        return HashCode.Combine(Username, Password, Email, Nickname);
    }
}