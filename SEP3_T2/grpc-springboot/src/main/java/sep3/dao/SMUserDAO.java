package sep3.dao;

import sep3.dto.smuser.*;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class SMUserDAO implements SMUserDAOInterface {
    private static SMUserDAO instance;

    private SMUserDAO() {}

    public static SMUserDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new SMUserDAO();
        }
        return instance;
    }

    @Override
    public void createUser(CreateSMUserDTO dto) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.social_media_user (username, password, nickname, email) VALUES (?,?,?,?)");
            statement.setString(1, dto.getUsername());
            statement.setString(2, dto.getPassword());
            statement.setString(3, dto.getNickname());
            statement.setString(4, dto.getEmail());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to create user");
        }
    }

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

    @Override
    public void deleteSMUser(int id) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            // First, delete the related posts
            PreparedStatement deletePostsStatement = connection.prepareStatement("DELETE FROM yapper_database.post WHERE userid = ?");
            deletePostsStatement.setInt(1, id);
            deletePostsStatement.executeUpdate();

            // Then, delete the user
            PreparedStatement deleteUserStatement = connection.prepareStatement("DELETE FROM yapper_database.social_media_user WHERE userid = ?");
            deleteUserStatement.setInt(1, id);
            deleteUserStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete user");
        }
    }

    public SMUserDTO getUserByUsername(String username) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.social_media_user WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new SMUserDTO(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        resultSet.getString("nickname"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                );
            } else {
                throw new SQLException("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get user by username");
        }
    }

    @Override
    public SMUserDTO getUserById(int id) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.social_media_user WHERE userid = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new SMUserDTO(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        resultSet.getString("nickname"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                );
            } else {
                throw new SQLException("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get user by id");
        }
    }

    @Override
    public ArrayList<SMUserDTO> getAllUsers() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.social_media_user");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<SMUserDTO> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(new SMUserDTO(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        resultSet.getString("nickname"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get all users");
        }
    }
}