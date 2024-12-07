namespace DTOs.Models;

public class SMUser
{
    public string Username { get; set; }
    public string Password { get; set; }
    public string Email { get; set; }
    public string Nickname { get; set; }
    public int Id { get; set; }
    public int followersCount { get; set; }
    public int followingCount { get; set; }
    
    
    public SMUser()
    {
        
    }
    
    public SMUser(string username, string password, string email, string nickname, int id, int followersCount, int followingCount)
    {
        Username = username;
        Password = password;
        Email = email;
        Nickname = nickname;
        Id = id;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }
}