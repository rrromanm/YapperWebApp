namespace Entities;

public class SocialMediaUser
{
    public string Username { get; set; }
    public string Password { get; set; }
    public string Email { get; set; }
    
    public SocialMediaUser()
    {
        
    }
    
    public SocialMediaUser(string username, string password, string email)
    {
        Username = username;
        Password = password;
        Email = email;
    }
}