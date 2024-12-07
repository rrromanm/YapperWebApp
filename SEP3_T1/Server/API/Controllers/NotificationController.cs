using App.LogicInterfaces;
using DTOs.DTOs.Notification;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class NotificationController : ControllerBase
{
    private readonly INotificationLogic _notificationLogic;

    public NotificationController(INotificationLogic notificationLogic)
    {
        _notificationLogic = notificationLogic;
    }

    [HttpPost]
    public async Task<ActionResult> SendNotificationAsync(NotificationDTO dto)
    {
        await _notificationLogic.SendNotificationAsync(dto);
        return Ok();
    }

    [HttpGet ("{userId}")]
    public async Task<ActionResult> GetNotificationsAsync(int userId)
    {
        var notifications = await _notificationLogic.GetNotificationsAsync(userId);
        return Ok(notifications);
    }
}