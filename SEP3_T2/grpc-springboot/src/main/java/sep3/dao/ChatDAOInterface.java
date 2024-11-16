package sep3.dao;

import sep3.dto.MessageDTO;
import sep3.dto.SendMessageDTO;

import java.sql.SQLException;
import java.util.List;

public interface ChatDAOInterface {
    void sendMessage(SendMessageDTO dto) throws SQLException;
    List<MessageDTO> getMessages(int senderId, int receiverId) throws SQLException;
    List<MessageDTO> getAllMessages() throws SQLException;
}