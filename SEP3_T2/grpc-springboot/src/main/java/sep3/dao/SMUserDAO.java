package sep3.dao;

import sep3.dto.smuser.*;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class SMUserDAO implements SMUserDAOInterface {
    private static SMUserDAO instance;

    private SMUserDAO() {
    }

    public static SMUserDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new SMUserDAO();
        }
        return instance;
    }

    /**
     * Creates a new user in the system with the provided details.
     *
     * @param dto The data transfer object containing the user details.
     * @return The ID of the newly created user.
     * @throws SQLException if an error occurs while inserting the user into the database.
     */
    @Override
    public int createUser(CreateSMUserDTO dto) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO yapper_database.social_media_user (username, password, nickname, email) VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, dto.getUsername());
            statement.setString(2, dto.getPassword());
            statement.setString(3, dto.getNickname());
            statement.setString(4, dto.getEmail());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(5);
            } else {
                throw new SQLException("Failed to create user, no ID obtained.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to create user");
        }
    }

    /**
     * Updates the email address of a specific user.
     *
     * @param userId The ID of the user to update.
     * @param email The new email address.
     * @throws SQLException if an error occurs while updating the user's email.
     */
    @Override
    public void updateEmail(int userId, String email) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE yapper_database.social_media_user SET email = ? WHERE userid = ?");
            statement.setString(1, email);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Updates the nickname of a specific user.
     *
     * @param userId The ID of the user to update.
     * @param nickname The new nickname.
     * @throws SQLException if an error occurs while updating the user's nickname.
     */
    @Override
    public void updateNickname(int userId, String nickname) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE yapper_database.social_media_user SET nickname = ? WHERE userid = ?");
            statement.setString(1, nickname);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Updates the password of a specific user.
     *
     * @param userId The ID of the user to update.
     * @param password The new password.
     * @throws SQLException if an error occurs while updating the user's password.
     */
    @Override
    public void updatePassword(int userId, String password) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE yapper_database.social_media_user SET password = ? WHERE userid = ?");
            statement.setString(1, password);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Deletes a user and all their posts from the system.
     *
     * @param id The ID of the user to delete.
     * @throws SQLException if an error occurs while deleting the user or their posts.
     */
    @Override
    public void deleteSMUser(int id) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement deletePostsStatement = connection.prepareStatement("DELETE FROM yapper_database.post WHERE userid = ?");
            deletePostsStatement.setInt(1, id);
            deletePostsStatement.executeUpdate();

            PreparedStatement deleteUserStatement = connection.prepareStatement("DELETE FROM yapper_database.social_media_user WHERE userid = ?");
            deleteUserStatement.setInt(1, id);
            deleteUserStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete user");
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user.
     * @return The user data transfer object containing the user's details.
     * @throws SQLException if an error occurs while retrieving the user by username.
     */
    public SMUserDTO getUserByUsername(String username) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement userStatement = connection.prepareStatement(
                    "SELECT * FROM yapper_database.social_media_user WHERE username = ?"
            );
            userStatement.setString(1, username);
            ResultSet userResultSet = userStatement.executeQuery();

            if (userResultSet.next()) {
                int userId = userResultSet.getInt("userid");

                PreparedStatement followersStatement = connection.prepareStatement(
                        "SELECT COUNT(*) AS followersCount FROM yapper_database.follows WHERE followedid = ?"
                );
                followersStatement.setInt(1, userId);
                ResultSet followersResultSet = followersStatement.executeQuery();
                int followersCount = 0;
                if (followersResultSet.next()) {
                    followersCount = followersResultSet.getInt("followersCount");
                }

                PreparedStatement followingsStatement = connection.prepareStatement(
                        "SELECT COUNT(*) AS followingCount FROM yapper_database.follows WHERE followerid = ?"
                );
                followingsStatement.setInt(1, userId);
                ResultSet followingsResultSet = followingsStatement.executeQuery();
                int followingCount = 0;
                if (followingsResultSet.next()) {
                    followingCount = followingsResultSet.getInt("followingCount");
                }

                return new SMUserDTO(
                        userId,
                        userResultSet.getString("username"),
                        userResultSet.getString("nickname"),
                        userResultSet.getString("password"),
                        userResultSet.getString("email"),
                        followersCount,
                        followingCount
                );
            } else {
                throw new SQLException("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get user by username");
        }
    }

    /**
     * Retrieves a user by their user ID.
     *
     * @param id The ID of the user.
     * @return The user data transfer object containing the user's details.
     * @throws SQLException if an error occurs while retrieving the user by ID.
     */
    @Override
    public SMUserDTO getUserById(int id) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement userStatement = connection.prepareStatement(
                    "SELECT * FROM yapper_database.social_media_user WHERE userid = ?"
            );
            userStatement.setInt(1, id);
            ResultSet userResultSet = userStatement.executeQuery();

            if (userResultSet.next()) {
                PreparedStatement followersStatement = connection.prepareStatement(
                        "SELECT COUNT(*) AS followersCount FROM yapper_database.follows WHERE followedid = ?"
                );
                followersStatement.setInt(1, id);
                ResultSet followersResultSet = followersStatement.executeQuery();
                int followersCount = 0;
                if (followersResultSet.next()) {
                    followersCount = followersResultSet.getInt("followersCount");
                }

                PreparedStatement followingsStatement = connection.prepareStatement(
                        "SELECT COUNT(*) AS followingCount FROM yapper_database.follows WHERE followerid = ?"
                );
                followingsStatement.setInt(1, id);
                ResultSet followingsResultSet = followingsStatement.executeQuery();
                int followingCount = 0;
                if (followingsResultSet.next()) {
                    followingCount = followingsResultSet.getInt("followingCount");
                }

                return new SMUserDTO(
                        userResultSet.getInt("userid"),
                        userResultSet.getString("username"),
                        userResultSet.getString("nickname"),
                        userResultSet.getString("password"),
                        userResultSet.getString("email"),
                        followersCount,
                        followingCount
                );
            } else {
                throw new SQLException("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get user by id");
        }
    }

    /**
     * Retrieves all users from the system.
     *
     * @return A list of user data transfer objects representing all users.
     * @throws SQLException if an error occurs while retrieving all users.
     */
    @Override
    public ArrayList<SMUserDTO> getAllUsers() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement userStatement = connection.prepareStatement(
                    "SELECT * FROM yapper_database.social_media_user"
            );
            ResultSet userResultSet = userStatement.executeQuery();
            ArrayList<SMUserDTO> users = new ArrayList<>();

            while (userResultSet.next()) {
                int userId = userResultSet.getInt("userid");

                PreparedStatement followersStatement = connection.prepareStatement(
                        "SELECT COUNT(*) AS followersCount FROM yapper_database.follows WHERE followedid = ?"
                );
                followersStatement.setInt(1, userId);
                ResultSet followersResultSet = followersStatement.executeQuery();
                int followersCount = 0;
                if (followersResultSet.next()) {
                    followersCount = followersResultSet.getInt("followersCount");
                }

                PreparedStatement followingsStatement = connection.prepareStatement(
                        "SELECT COUNT(*) AS followingCount FROM yapper_database.follows WHERE followerid = ?"
                );
                followingsStatement.setInt(1, userId);
                ResultSet followingsResultSet = followingsStatement.executeQuery();
                int followingCount = 0;
                if (followingsResultSet.next()) {
                    followingCount = followingsResultSet.getInt("followingCount");
                }

                users.add(new SMUserDTO(
                        userId,
                        userResultSet.getString("username"),
                        userResultSet.getString("nickname"),
                        userResultSet.getString("password"),
                        userResultSet.getString("email"),
                        followersCount,
                        followingCount
                ));
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get all users");
        }
    }

    /**
     * Follows a user.
     *
     * @param followerId The ID of the user who is following.
     * @param followedId The ID of the user being followed.
     * @throws SQLException if an error occurs while inserting the follow relationship.
     */
    @Override public void followUser(int followerId, int followedId) throws SQLException
    {
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO follows (followerId, followedId) VALUES (?, ?)");
            statement.setInt(1, followerId);
            statement.setInt(2, followedId);
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("Failed to follow user");
        }

    }

    /**
     * Unfollows a user.
     *
     * @param followerId The ID of the user who is unfollowing.
     * @param followedId The ID of the user being unfollowed.
     * @throws SQLException if an error occurs while deleting the follow relationship.
     */
    @Override public void unfollowUser(int followerId, int followedId)
        throws SQLException
    {
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM follows WHERE followerId = ? AND followedId = ?");
            statement.setInt(1, followerId);
            statement.setInt(2, followedId);
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("Failed to unfollow user");
        }
    }

    /**
     * Retrieves a list of followers for a specific user.
     *
     * @param userId The ID of the user whose followers are being retrieved.
     * @return A list of follower data transfer objects representing the user's followers.
     * @throws SQLException if an error occurs while retrieving the followers.
     */
    @Override
    public ArrayList<FollowerDTO> getFollowers(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM yapper_database.follows JOIN yapper_database.social_media_user ON follows.followerid = social_media_user.userid WHERE follows.followedid = ?"
            );
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<FollowerDTO> followers = new ArrayList<>();

            while (resultSet.next()) {
                FollowerDTO follower = new FollowerDTO(
                        resultSet.getInt("userid"),
                        resultSet.getString("nickname"),
                        resultSet.getString("username")
                );
                followers.add(follower);
                System.out.println("Follower: " + follower.getUsername() + " (" + follower.getNickname() + ")");
            }
            return followers;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get followers");
        }
    }

    /**
     * Retrieves a list of users being followed by a specific user.
     *
     * @param userId The ID of the user whose following list is being retrieved.
     * @return A list of follower data transfer objects representing the users the user is following.
     * @throws SQLException if an error occurs while retrieving the following list.
     */
    @Override
    public ArrayList<FollowerDTO> getFollowing(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM yapper_database.follows JOIN yapper_database.social_media_user ON follows.followedid = social_media_user.userid WHERE follows.followerid = ?"
            );
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<FollowerDTO> following = new ArrayList<>();

            while (resultSet.next()) {
                FollowerDTO follow = new FollowerDTO(
                        resultSet.getInt("userid"),
                        resultSet.getString("nickname"),
                        resultSet.getString("username")
                );
                following.add(follow);
                System.out.println("Following: " + follow.getUsername() + " (" + follow.getNickname() + ")");
            }
            return following;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get following");
        }
    }

    /**
     * Checks if a user is following another user.
     *
     * @param followerId The ID of the user who may be following.
     * @param followedId The ID of the user being followed.
     * @return True if the follower is following the followed user, false otherwise.
     * @throws SQLException if an error occurs while checking the follow status.
     */
    @Override
    public boolean isFollowing(int followerId, int followedId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM yapper_database.follows WHERE followerid = ? AND followedid = ?"
            );
            statement.setInt(1, followerId);
            statement.setInt(2, followedId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to check follow status");
        }
    }

    /**
     * Retrieves a list of three random users that the given user is not following.
     *
     * @param userId The ID of the user to exclude from the random selection.
     * @return A list of follower data transfer objects representing the three random users.
     * @throws SQLException if an error occurs while retrieving the random users.
     */
    @Override
    public ArrayList<FollowerDTO> getThreeRandomUsers(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT social_media_user.username, \n" +
                            "       social_media_user.nickname, \n" +
                            "       social_media_user.userid \n" +
                            "FROM yapper_database.social_media_user \n" +
                            "WHERE social_media_user.userid != ? \n" +
                            "ORDER BY random() \n" +
                            "LIMIT 3;\n;"
            );
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            ArrayList<FollowerDTO> followers = new ArrayList<>();
            while (resultSet.next()) {
                FollowerDTO follower = new FollowerDTO(
                        resultSet.getInt("userid"),
                        resultSet.getString("nickname"),
                        resultSet.getString("username")
                );

                followers.add(follower);
            }
            return followers;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to get random users");
        }
    }

    /**
     * Searches for users by username or nickname.
     *
     * @param search The search term to match against usernames or nicknames.
     * @return A list of follower data transfer objects representing the matching users.
     * @throws SQLException if an error occurs while searching for users.
     */
    @Override
    public ArrayList<FollowerDTO> getUsersBySearch(String search) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT smu.userid, smu.username, smu.nickname\n" +
                    "FROM yapper_database.social_media_user smu\n" +
                    "WHERE LOWER(smu.username) LIKE LOWER(?)\n" +
                    "   OR LOWER(smu.nickname) LIKE LOWER(?)\n" +
                    "ORDER BY smu.username ASC;\n"
            );
            statement.setString(1,"%" + search + "%");
            statement.setString(2, "%" + search + "%");

            ResultSet resultSet = statement.executeQuery();
            ArrayList<FollowerDTO> followers = new ArrayList<>();

            while (resultSet.next()) {
                FollowerDTO follower = new FollowerDTO(
                        resultSet.getInt("userid"),
                        resultSet.getString("nickname"),
                        resultSet.getString("username")
                );

                followers.add(follower);
            }
            return followers;
        }
    }
}
