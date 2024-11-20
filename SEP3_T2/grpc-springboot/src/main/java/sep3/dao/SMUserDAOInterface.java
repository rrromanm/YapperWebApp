package sep3.dao;

import sep3.dto.smuser.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SMUserDAOInterface
{
    void createUser(CreateSMUserDTO dto) throws SQLException;
    void updateSMUser(UpdateSMUserDTO dto) throws SQLException;
    void deleteSMUser(int id) throws SQLException;
    ArrayList<SMUserDTO> getAllUsers() throws SQLException;
    SMUserDTO getUserByUsername(String username) throws SQLException;
    SMUserDTO getUserById(int id) throws SQLException;
}
