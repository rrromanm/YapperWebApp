package sep3.dto.moderator;

/**
 * Data transfer object for representing a moderator.
 */
public class ModeratorDTO {
    private int id;
    private String username;
    private String password;

    /**
     * Constructs a new ModeratorDTO with the provided details.
     *
     * @param id The ID of the moderator.
     * @param username The username of the moderator.
     * @param password The password of the moderator.
     */
    public ModeratorDTO(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the ID of the moderator.
     *
     * @return The moderator's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the username of the moderator.
     *
     * @return The moderator's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the password of the moderator.
     *
     * @return The moderator's password.
     */
    public String getPassword() {
        return password;
    }
}
