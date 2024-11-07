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

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=yapper_schema", "postgres", "343460");

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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.social_media_user (username, password, nickname, email) VALUES (?,?,?,?)");
            statement.setString(1, dto.getUsername());
            statement.setString(2, dto.getPassword());
            statement.setString(3, dto.getNickname());
            statement.setString(4, dto.getEmail());
            statement.executeUpdate();
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

    }
}
