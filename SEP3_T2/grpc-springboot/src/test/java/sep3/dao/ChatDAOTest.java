package sep3.dao;

import org.junit.jupiter.api.*;
import sep3.dto.chat.MessageDTO;
import sep3.dto.chat.SendMessageDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatDAOTest {
    private ChatDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = ChatDAO.getInstance();

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM message");
            statement.executeUpdate();
        }
    }

    @Test
    void sendMessageInsertsMessageIntoDatabase() throws SQLException {
        SendMessageDTO sendDto = new SendMessageDTO("Test Message", 1, 2);
        dao.sendMessage(sendDto);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM message WHERE body = ?");
            statement.setString(1, "Test Message");
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("Test Message", resultSet.getString("body"));
            assertEquals(2, resultSet.getInt("senderId"));
            assertEquals(1, resultSet.getInt("receiverId"));
        }
    }

    @Test
    void getMessagesReturnsMessagesForGivenSenderAndReceiver() throws SQLException {
        SendMessageDTO sendDto1 = new SendMessageDTO("Message 1", 1, 2);
        SendMessageDTO sendDto2 = new SendMessageDTO("Message 2", 2, 1);
        dao.sendMessage(sendDto1);
        dao.sendMessage(sendDto2);

        List<MessageDTO> messages = dao.getMessages(1, 2);
        assertEquals(2, messages.size());
    }

    @Test
    void getAllMessagesReturnsAllMessages() throws SQLException {
        dao.sendMessage(new SendMessageDTO("Message 1", 1, 2));
        dao.sendMessage(new SendMessageDTO("Message 2", 2, 1));

        List<MessageDTO> allMessages = dao.getAllMessages();
        assertEquals(2, allMessages.size());
    }

    @Test
    void getMessagesReturnsMessagesInCorrectOrder() throws SQLException {
        SendMessageDTO sendDto1 = new SendMessageDTO("Message 1", 1, 2);
        SendMessageDTO sendDto2 = new SendMessageDTO("Message 2", 2, 1);
        dao.sendMessage(sendDto1);
        dao.sendMessage(sendDto2);

        List<MessageDTO> messages = dao.getMessages(1, 2);
        assertEquals("Message 1", messages.get(0).getMessage());
        assertEquals("Message 2", messages.get(1).getMessage());
    }

    @Test
    void sendMessageThrowsSQLExceptionWhenInsertionFails() {
        SendMessageDTO invalidDto = new SendMessageDTO(null, 1, 2);
        assertThrows(SQLException.class, () -> dao.sendMessage(invalidDto));
    }

    @Test
    void getMessagesReturnsEmptyListForNoMessages() throws SQLException {
        List<MessageDTO> messages = dao.getMessages(1, 2);
        assertTrue(messages.isEmpty());
    }

    @Test
    void getAllMessagesReturnsMessagesSortedByDate() throws SQLException {
        SendMessageDTO sendDto1 = new SendMessageDTO("First Message", 1, 2);
        SendMessageDTO sendDto2 = new SendMessageDTO("Second Message", 2, 1);
        dao.sendMessage(sendDto1);
        dao.sendMessage(sendDto2);

        List<MessageDTO> allMessages = dao.getAllMessages();
        assertTrue(allMessages.get(0).getMessage().compareTo(allMessages.get(1).getMessage()) < 0);
    }
}
