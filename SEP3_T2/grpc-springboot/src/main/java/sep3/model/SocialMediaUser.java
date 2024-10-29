package sep3.model;

public class SocialMediaUser extends User
{
    public int followersCount;
    public int followingCount;
    public String email;

    public SocialMediaUser(String username, String password, String email)
    {
        super(username, password);
        this.email = email;
        this.followersCount = 0;
        this.followingCount = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
