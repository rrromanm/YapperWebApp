package sep3.dto.smuser;

public class UpdateSMUserDTO
{
    private String username;
    private String password;
    private String nickname;
    private String email;

    public UpdateSMUserDTO(final String username, final String password, final String nickname, final String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getEmail() {
        return this.email;
    }

}
