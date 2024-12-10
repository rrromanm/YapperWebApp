package sep3.dao;

import sep3.dto.moderator.ModeratorDTO;

import java.sql.SQLException;

public interface ModeratorDAOInterface
{
    ModeratorDTO getModeratorByUserName(String username) throws SQLException;
}
