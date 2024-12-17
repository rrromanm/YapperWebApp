package sep3.dto.smuser;

/**
 * Data transfer object for creating a social media user with necessary
 * details such as username, nickname, password, and email.
 */
public class CreateSMUserDTO {
    private String username;
    private String nickname;
    private String password;
    private String email;

    /**
     * Constructs a new CreateSMUserDTO with the specified username, nickname,
     * password, and email.
     *
     * @param username The username of the social media user.
     * @param nickname The nickname of the social media user.
     * @param password The password for the social media user.
     * @param email    The email address of the social media user.
     */
    public CreateSMUserDTO(String username, String nickname, String password, String email) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    /**
     * Retrieves the nickname of the social media user.
     *
     * @return The nickname of the social media user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Retrieves the username of the social media user.
     *
     * @return The username of the social media user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the password of the social media user.
     *
     * @return The password of the social media user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the email address of the social media user.
     *
     * @return The email address of the social media user.
     */
    public String getEmail() {
        return email;
    }
}
