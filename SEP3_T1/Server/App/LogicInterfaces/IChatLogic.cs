using DTOs.DTOs;

namespace App.LogicInterfaces;

public interface IChatLogic
{
    Task SendMessageAsync(SendMessageDTO dto);
    Task<List<MessageDTO>> GetMessagesAsync(int senderId, int receiverId);
    Task<List<MessageDTO>> GetAllMessagesAsync();
}