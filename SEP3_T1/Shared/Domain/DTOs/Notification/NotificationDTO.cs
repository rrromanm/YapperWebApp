namespace DTOs.DTOs.Notification;

public class NotificationDTO
{
    public string NotificationMessage { get; set; }
    public int UserId { get; set; }
    public string Timestamp { get; set; }

    public override string ToString()
    {
        return $"{DateTime.Parse(Timestamp).ToString("dd MMMM yyyy, HH:mm")} - {NotificationMessage}";
    }

}

