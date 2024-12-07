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

    private ChatDAO() {}

    public static ChatDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ChatDAO();
        }
        return instance;
    }

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

    @Override
    public List<MessageDTO> getMessages(int senderId, int receiverId) throws SQLException {
        List<MessageDTO> messages = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            // Updated query to include both directions
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