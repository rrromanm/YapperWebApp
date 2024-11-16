package sep3.dao;

import sep3.dto.smuser.CreateSMUserDTO;
import sep3.dto.smuser.UpdateSMUserDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SMUserDAO implements SMUserDAOInterface {
    private static SMUserDAO instance;

    private SMUserDAO() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=yapper_database", "postgres", "via");

    }

    public static SMUserDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new SMUserDAO();
        }
        return instance;
    }

    @Override
    public void createUser(CreateSMUserDTO dto) throws SQLException {
        try{
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.social_media_user (username, password, nickname, email) VALUES (?,?)");
            statement.setString(1, dto.getUsername());

        }
        catch (Exception e){
            e.printStackTrace();
            throw new SQLException("Failed to create user");
        }
    }

    @Override
    public void updateSMUser(UpdateSMUserDTO dto) throws SQLException {

    }

    @Override
    public void deleteSMUser(int id) throws SQLException {
        try {
            Connection connection = getConnection();

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

}
