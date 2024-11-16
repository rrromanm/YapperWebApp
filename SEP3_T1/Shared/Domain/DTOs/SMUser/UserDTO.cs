namespace DTOs.User;

public class UserDTO
{
    public string Password { get; set; }
    public string Email { get; set; }
    public string Username { get; set; }
    public string Nickname { get; set; }
    
    public UserDTO(string username, string password, string email, string nickname)
    {
        Username = username;
        Password = password;
        Email = email;
        Nickname = nickname;
    }
    
    public UserDTO(string username, string password, string email)
    {
        Username = username;
        Password = password;
        Email = email;
    }
}