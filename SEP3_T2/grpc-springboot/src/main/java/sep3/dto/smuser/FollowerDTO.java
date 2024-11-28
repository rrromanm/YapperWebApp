package sep3.dto.smuser;

public class FollowerDTO
{
    private int id;
    private String Nickname;
    private String username;

    public FollowerDTO(int id, String Nickname, String username)
    {
        this.id = id;
        this.Nickname = Nickname;
        this.username = username;
    }

    public int getId()
    {
        return id;
    }

    public String getNickname()
    {
        return Nickname;
    }

    public String getUsername()
    {
        return username;
    }
}
