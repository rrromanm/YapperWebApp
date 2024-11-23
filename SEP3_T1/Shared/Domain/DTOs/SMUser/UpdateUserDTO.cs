namespace DTOs.User;

public class UpdateUserDTO
{
    public int UserId { get; set; }
    public string? Email { get; set; }
    public string? Password { get; set; }
    public string? Nickname { get; set; }
}