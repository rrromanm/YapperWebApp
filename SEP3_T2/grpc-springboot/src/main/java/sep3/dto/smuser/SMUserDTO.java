package sep3.dto.smuser;

/**
 * Data transfer object representing a social media user.
 * Contains details about the user's profile, including username, nickname, email, password,
 * and their follower and following counts.
 */
public class SMUserDTO {
    private int id;
    private String username;
    private String nickname;
    private String password;
    private String email;

    private int followerCount;
    private int followingCount;

    /**
     * Constructs a new SMUserDTO with the specified user details.
     *
     * @param id            The unique identifier of the user.
     * @param username      The username of the user.
     * @param nickname      The nickname of the user.
     * @param password      The password of the user.
     * @param email         The email address of the user.
     * @param followerCount The number of followers the user has.
     * @param followingCount The number of people the user is following.
     */
    public SMUserDTO(int id, String username, String nickname, String password, String email, int followerCount, int followingCount) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the nickname of the user.
     *
     * @return The nickname of the user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the number of people the user is following.
     *
     * @return The following count of the user.
     */
    public int getFollowingCount() {
        return followingCount;
    }

    /**
     * Retrieves the number of followers the user has.
     *
     * @return The follower count of the user.
     */
    public int getFollowerCount() {
        return followerCount;
    }
}
