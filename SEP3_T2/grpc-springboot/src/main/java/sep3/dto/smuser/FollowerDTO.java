package sep3.dto.smuser;

/**
 * Data transfer object representing a follower of a social media user.
 * Contains information about the follower's ID, nickname, and username.
 */
public class FollowerDTO {
    private int id;
    private String Nickname;
    private String username;

    /**
     * Constructs a new FollowerDTO with the specified follower details.
     *
     * @param id       The unique identifier of the follower.
     * @param Nickname The nickname of the follower.
     * @param username The username of the follower.
     */
    public FollowerDTO(int id, String Nickname, String username) {
        this.id = id;
        this.Nickname = Nickname;
        this.username = username;
    }

    /**
     * Retrieves the unique identifier of the follower.
     *
     * @return The ID of the follower.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the nickname of the follower.
     *
     * @return The nickname of the follower.
     */
    public String getNickname() {
        return Nickname;
    }

    /**
     * Retrieves the username of the follower.
     *
     * @return The username of the follower.
     */
    public String getUsername() {
        return username;
    }
}
