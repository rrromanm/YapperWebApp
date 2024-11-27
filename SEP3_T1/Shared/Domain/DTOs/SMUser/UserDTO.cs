namespace DTOs.User;

public class UserDTO
{
    public string Password { get; set; }
    public string Email { get; set; }
    public string Username { get; set; }
    public string Nickname { get; set; }
    public int Id { get; set; }
    public int followersCount { get; set; }
    public int followingCount { get; set; }
}