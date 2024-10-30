namespace DTOs;

public class CreateUserDTO
{
    public string Password { get; set; }
    public string Email { get; set; }
    public string Username { get; set; }
    
    public CreateUserDTO(string username, string password, string email)
    {
        Username = username;
        Password = password;
        Email = email;
    }
}