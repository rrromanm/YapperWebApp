package sep3.dto.smuser;

public class SMUserDTO
{
    private int id;
    private String username;
    private String nickname;
    private String password;
    private String email;

    private int followerCount;
    private int followingCount;

    public SMUserDTO(int id, String username, String nickname, String password, String email, int followerCount, int followingCount) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }
}
