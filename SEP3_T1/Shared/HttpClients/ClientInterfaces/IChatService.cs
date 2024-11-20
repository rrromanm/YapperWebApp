using DTOs.DTOs;

namespace HttpClients.ClientInterfaces;

public interface IChatService
{
    Task SendMessage(SendMessageDTO dto);
    Task<List<MessageDTO>> GetAllMessages();
    Task<List<MessageDTO>> GetMessages(int sender, int receiver);
}