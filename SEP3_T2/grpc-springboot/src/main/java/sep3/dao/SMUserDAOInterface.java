package sep3.dao;

import sep3.dto.smuser.CreateSMUserDTO;
import sep3.dto.smuser.UpdateSMUserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SMUserDAOInterface
{
    void createUser(CreateSMUserDTO dto) throws SQLException;
    void updateSMUser(UpdateSMUserDTO dto) throws SQLException;
    void deleteSMUser(int id) throws SQLException;
}
