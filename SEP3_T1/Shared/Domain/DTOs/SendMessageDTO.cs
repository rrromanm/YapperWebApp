using System.Text.Json.Serialization;

namespace DTOs.DTOs
{
    public class SendMessageDTO
    {
        [JsonPropertyName("message")]
        public string Message { get; set; }
        
        [JsonPropertyName("senderId")]
        public int SenderId { get; set; }
        
        [JsonPropertyName("receiverId")]
        public int ReceiverId { get; set; }

        public SendMessageDTO(string message, int senderId, int receiverId)
        {
            Message = message;
            SenderId = senderId;
            ReceiverId = receiverId;
        }
    }
}