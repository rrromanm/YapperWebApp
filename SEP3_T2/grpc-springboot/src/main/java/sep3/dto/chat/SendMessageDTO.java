package sep3.dto.chat;

public class SendMessageDTO {
    private String message;
    private int receiver;
    private int sender;

    public SendMessageDTO(String message, int receiver, int sender) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public int getReceiver() {
        return receiver;
    }

    public int getSender() {
        return sender;
    }
}
