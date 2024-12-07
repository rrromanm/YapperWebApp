using DTOs.DTOs.Notification;

namespace HttpClients.ClientInterfaces;

public interface INotificationService
{
    Task SendNotificationAsync(NotificationDTO dto);
    Task<List<NotificationDTO>> GetNotificationsAsync(int userId);
}