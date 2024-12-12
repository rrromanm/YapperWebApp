using App.Logic;
using DTOs.DTOs.Notification;
using NUnit.Framework;

namespace Logic;

public class NotificationLogicTests
{
    private NotificationLogic notificationLogic;
    
    [SetUp]
    public void Setup()
    {
        notificationLogic = new NotificationLogic(new GRPCService());
    }
    
    [Test]
    public async Task sending_notification_sends_notification()
    {
        NotificationDTO dto = new NotificationDTO();
        dto.UserId = 1;
        dto.NotificationMessage = "Test";
        Assert.DoesNotThrowAsync(() => notificationLogic.SendNotificationAsync(dto));
        List<NotificationDTO> notifications = await notificationLogic.GetNotificationsAsync(1);
        Assert.That(notifications.Count, Is.EqualTo(1));
        Assert.That(notifications[0].NotificationMessage, Is.EqualTo("Test"));
        Assert.That(notifications[0].UserId, Is.EqualTo(1));
    }
    
    [Test]
    public async Task getting_notifications_gets_notifications()
    {
        NotificationDTO dto = new NotificationDTO();
        dto.UserId = 1;
        dto.NotificationMessage = "Test";
        await notificationLogic.SendNotificationAsync(dto);
        List<NotificationDTO> notifications = await notificationLogic.GetNotificationsAsync(1);
        Assert.That(notifications.Count, Is.EqualTo(1));
        Assert.That(notifications[0].NotificationMessage, Is.EqualTo("Test"));
        Assert.That(notifications[0].UserId, Is.EqualTo(1));
    }
    
}