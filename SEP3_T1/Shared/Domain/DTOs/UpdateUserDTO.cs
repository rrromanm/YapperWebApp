namespace DTOs.User;

public class UpdateUserDTO
{
    public int AccountId { get; set; }
    public string? Email { get; set; }
    public string? Password { get; set; }
    public string? Nickname { get; set; }
    
    public UpdateUserDTO(int accountId, string? email, string? password, string? nickname)
    {
        AccountId = accountId;
        Email = email;
        Password = password;
        Nickname = nickname;
    }
}