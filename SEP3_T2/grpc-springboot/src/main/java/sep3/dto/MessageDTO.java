package sep3.dto;

public class MessageDTO {
    private String message;
    private int sender;
    private int receiver;
    private String timestamp;
    private int messageId;

    public MessageDTO(String message, int sender, int receiver, String timestamp, int messageId) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getMessageId() {
        return messageId;
    }
}