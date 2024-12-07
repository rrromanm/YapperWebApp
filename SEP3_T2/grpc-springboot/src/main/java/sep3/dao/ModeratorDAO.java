package sep3.dao;

import sep3.dto.moderator.ModeratorDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeratorDAO implements ModeratorDAOInterface {

    private static ModeratorDAO instance;

    public static ModeratorDAO getInstance() {
        if (instance == null) {
            instance = new ModeratorDAO();
        }
        return instance;
    }

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
