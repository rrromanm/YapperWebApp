package sep3.dto.chat;

/**
 * Data transfer object representing a message to be sent in a chat system.
 */
public class SendMessageDTO {
    private String message;
    private int receiver;
    private int sender;

    /**
     * Constructs a new SendMessageDTO with the provided message details.
     *
     * @param message The content of the message to be sent.
     * @param receiver The ID of the user receiving the message.
     * @param sender The ID of the user sending the message.
     */
    public SendMessageDTO(String message, int receiver, int sender) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
    }

    /**
     * Retrieves the content of the message to be sent.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the ID of the user receiving the message.
     *
     * @return The receiver's ID.
     */
    public int getReceiver() {
        return receiver;
    }

    /**
     * Retrieves the ID of the user sending the message.
     *
     * @return The sender's ID.
     */
    public int getSender() {
        return sender;
    }
}
