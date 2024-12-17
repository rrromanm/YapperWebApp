package sep3.dto.smuser;

/**
 * Data transfer object representing the updated details of a social media user.
 * Used for updating a user's profile information such as username, password, nickname, and email.
 */
public class UpdateSMUserDTO {
    private String username;
    private String password;
    private String nickname;
    private String email;

    /**
     * Constructs a new UpdateSMUserDTO with the specified updated user details.
     *
     * @param username The new username of the user.
     * @param password The new password of the user.
     * @param nickname The new nickname of the user.
     * @param email    The new email address of the user.
     */
    public UpdateSMUserDTO(final String username, final String password, final String nickname, final String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    /**
     * Retrieves the updated username of the user.
     *
     * @return The updated username of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Retrieves the updated password of the user.
     *
     * @return The updated password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Retrieves the updated nickname of the user.
     *
     * @return The updated nickname of the user.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Retrieves the updated email address of the user.
     *
     * @return The updated email address of the user.
     */
    public String getEmail() {
        return this.email;
    }
}
