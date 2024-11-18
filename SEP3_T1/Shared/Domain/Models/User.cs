namespace DTOs.Models;

public class User
{
    public string Username { get; set; }
    public string Password { get; set; }
    public string Email { get; set; }
    public string Nickname { get; set; }
    public int Id { get; set; }
    
    public User()
    {
        
    }
    
    public User(string username, string password, string email, string nickname, int id)
    {
        Username = username;
        Password = password;
        Email = email;
        Nickname = nickname;
        Id = id;
    }
}