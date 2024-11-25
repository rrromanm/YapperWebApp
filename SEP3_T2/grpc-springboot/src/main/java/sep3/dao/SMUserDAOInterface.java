package sep3.dao;

import sep3.dto.smuser.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SMUserDAOInterface
{
    void createUser(CreateSMUserDTO dto) throws SQLException;
    void updateEmail(int userId, String email) throws SQLException;
    void updateNickname(int userId, String nickname) throws SQLException;
    void updatePassword(int userId, String password) throws SQLException;
    void deleteSMUser(int id) throws SQLException;
    ArrayList<SMUserDTO> getAllUsers() throws SQLException;
    SMUserDTO getUserByUsername(String username) throws SQLException;
    SMUserDTO getUserById(int id) throws SQLException;
    void followUser(int followerId, int followingId) throws SQLException;
    void unfollowUser(int followerId, int followingId) throws SQLException;
}
