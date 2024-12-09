package sep3.dao;

import org.checkerframework.checker.units.qual.A;
import sep3.dto.smuser.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SMUserDAOInterface
{
    int createUser(CreateSMUserDTO dto) throws SQLException;
    void updateEmail(int userId, String email) throws SQLException;
    void updateNickname(int userId, String nickname) throws SQLException;
    void updatePassword(int userId, String password) throws SQLException;
    void deleteSMUser(int id) throws SQLException;
    ArrayList<SMUserDTO> getAllUsers() throws SQLException;
    SMUserDTO getUserByUsername(String username) throws SQLException;
    SMUserDTO getUserById(int id) throws SQLException;
    void followUser(int followerId, int followingId) throws SQLException;
    void unfollowUser(int followerId, int followingId) throws SQLException;
    ArrayList<FollowerDTO> getFollowers(int userId) throws SQLException;
    ArrayList<FollowerDTO> getFollowing(int userId) throws SQLException;
    boolean isFollowing(int followerId, int followedId) throws SQLException;
    ArrayList<FollowerDTO> getThreeRandomUsers(int userId) throws SQLException;
    ArrayList<FollowerDTO> getUsersBySearch (String search) throws SQLException;
}
