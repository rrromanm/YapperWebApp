package sep3.dao;

import sep3.dto.moderator.ModeratorDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeratorDAO implements ModeratorDAOInterface {

    private static ModeratorDAO instance;

    /**
     * Returns the singleton instance of {@code ModeratorDAO}.
     *
     * @return the singleton instance of {@code ModeratorDAO}
     */
    public static ModeratorDAO getInstance() {
        if (instance == null) {
            instance = new ModeratorDAO();
        }
        return instance;
    }

    /**
     * Retrieves a moderator's details from the database by their username.
     *
     * @param username the username of the moderator
     * @return a {@link ModeratorDTO} object containing the moderator's details
     * @throws SQLException if the moderator is not found or if there is a database error
     */
    @Override
    public ModeratorDTO getModeratorByUserName(String username) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.moderator WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new ModeratorDTO(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            } else {
                throw new SQLException("Moderator not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get moderator by username", e);
        }
    }
}