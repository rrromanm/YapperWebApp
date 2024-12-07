using DTOs.DTOs.Notification;

namespace App.LogicInterfaces;

public interface INotificationLogic
{
    Task SendNotificationAsync(NotificationDTO dto);
    Task<List<NotificationDTO>> GetNotificationsAsync(int userId);
}