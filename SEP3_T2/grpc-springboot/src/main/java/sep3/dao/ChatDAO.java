package sep3.dao;

import sep3.dto.chat.MessageDTO;
import sep3.dto.chat.SendMessageDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements ChatDAOInterface {

    private static ChatDAO instance;

    /**
     * Private constructor to prevent direct instantiation.
     * Use {@link #getInstance()} to get the singleton instance.
     */
    private ChatDAO() {}

    /**
     * Retrieves the singleton instance of {@code ChatDAO}.
     *
     * @return the singleton instance of {@code ChatDAO}
     * @throws SQLException if a database access error occurs
     */
    public static ChatDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ChatDAO();
        }
        return instance;
    }

    /**
     * Sends a chat message by inserting the message details into the database.
     *
     * @param dto the {@link SendMessageDTO} object containing the message details
     * @throws SQLException if there is an error during the insert operation
     */
    @Override
    public void sendMessage(SendMessageDTO dto) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO message (body, senderId, receiverId) VALUES (?,?,?)");
            statement.setString(1, dto.getMessage());
            statement.setInt(2, dto.getSender());
            statement.setInt(3, dto.getReceiver());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to send message");
        }
    }

    /**
     * Retrieves all messages exchanged between a sender and a receiver, sorted by date.
     *
     * @param senderId the ID of the message sender
     * @param receiverId the ID of the message receiver
     * @return a {@link List} of {@link MessageDTO} objects representing the chat messages
     * @throws SQLException if there is an error during the retrieval
     */
    @Override
    public List<MessageDTO> getMessages(int senderId, int receiverId) throws SQLException {
        List<MessageDTO> messages = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM message WHERE (senderid = ? AND receiverid = ?) OR (senderid = ? AND receiverid = ?) ORDER BY date ASC"
            );
            statement.setInt(1, senderId);
            statement.setInt(2, receiverId);
            statement.setInt(3, receiverId);
            statement.setInt(4, senderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(new MessageDTO(
                        resultSet.getString("body"),
                        resultSet.getInt("senderid"),
                        resultSet.getInt("receiverid"),
                        resultSet.getString("date"),
                        resultSet.getInt("messageid")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get messages");
        }
        return messages;
    }

    /**
     * Retrieves all messages from the message table in the database.
     *
     * @return a {@link List} of {@link MessageDTO} objects representing all messages in the database
     * @throws SQLException if there is an error during the retrieval
     */
    @Override
    public List<MessageDTO> getAllMessages() throws SQLException {
        List<MessageDTO> messages = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM message");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(new MessageDTO(
                        resultSet.getString("body"),
                        resultSet.getInt("senderId"),
                        resultSet.getInt("receiverId"),
                        resultSet.getString("date"),
                        resultSet.getInt("messageId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get all messages");
        }
        return messages;
    }
}