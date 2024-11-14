using System.Text.Json.Serialization;

namespace DTOs.DTOs
{
    public class MessageDTO
    {
        [JsonPropertyName("messageId")]
        public int MessageId { get; set; }

        [JsonPropertyName("message")]
        public string Message { get; set; }

        [JsonPropertyName("senderId")]
        public int SenderId { get; set; }

        [JsonPropertyName("receiverId")]
        public int ReceiverId { get; set; }

        [JsonPropertyName("timestamp")]
        public string Timestamp { get; set; }

        [JsonConstructor]
        public MessageDTO(int messageId, string message, int senderId, int receiverId, string timestamp)
        {
            MessageId = messageId;
            Message = message;
            SenderId = senderId;
            ReceiverId = receiverId;
            Timestamp = timestamp;
        }
    }
}