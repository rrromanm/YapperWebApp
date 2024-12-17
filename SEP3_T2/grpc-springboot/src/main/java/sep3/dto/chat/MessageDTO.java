package sep3.dto.chat;

/**
 * Data transfer object representing a message exchanged between users in a chat system.
 */
public class MessageDTO {
    private String message;
    private int sender;
    private int receiver;
    private String timestamp;
    private int messageId;

    /**
     * Constructs a new MessageDTO with the provided message details.
     *
     * @param message The content of the message.
     * @param sender The ID of the sender of the message.
     * @param receiver The ID of the receiver of the message.
     * @param timestamp The timestamp when the message was sent.
     * @param messageId The ID of the message.
     */
    public MessageDTO(String message, int sender, int receiver, String timestamp, int messageId) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.messageId = messageId;
    }

    /**
     * Retrieves the content of the message.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the ID of the sender of the message.
     *
     * @return The sender's ID.
     */
    public int getSender() {
        return sender;
    }

    /**
     * Retrieves the ID of the receiver of the message.
     *
     * @return The receiver's ID.
     */
    public int getReceiver() {
        return receiver;
    }

    /**
     * Retrieves the timestamp when the message was sent.
     *
     * @return The message timestamp.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieves the ID of the message.
     *
     * @return The message ID.
     */
    public int getMessageId() {
        return messageId;
    }
}
