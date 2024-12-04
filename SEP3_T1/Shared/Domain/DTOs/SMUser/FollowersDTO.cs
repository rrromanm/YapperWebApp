namespace DTOs.User;

public class FollowersDTO
{
    public int id { get; set; }
    public string username { get; set; }
    public string nickname { get; set; }
    public bool IsFollowedByCurrentUser { get; set; }
}