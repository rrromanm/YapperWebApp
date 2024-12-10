package sep3.dto.moderator;

public class ModeratorDTO
{
    private int id;
    private String username;
    private String password;

    public ModeratorDTO(int id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
