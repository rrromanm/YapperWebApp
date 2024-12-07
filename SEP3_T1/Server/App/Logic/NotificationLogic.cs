using System.Text.Json;
using App.LogicInterfaces;
using DTOs.DTOs.Notification;
using Grpc.Net.Client;
using GrpcClient;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace App.Logic;

public class NotificationLogic : INotificationLogic
{
    private NotificationService.NotificationServiceClient client;
    
    public NotificationLogic(GRPCService service)
    {
        GrpcChannel channel = service.Channel;
        client = new NotificationService.NotificationServiceClient(channel);
    }
    public async Task SendNotificationAsync(NotificationDTO dto)
    {
        await client.SendNotificationAsync(new NotificationRequest
        {
            UserId = dto.UserId,
            NotificationMessage = dto.NotificationMessage
             
        });
    }

    public async Task<List<NotificationDTO>> GetNotificationsAsync(int userId)
    {
        try
        {
            GetNotificationResponse response = await client.GetNotificationsAsync(new GetNotificationRequest
            {
                UserId = userId
            });

            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            };

            return JsonSerializer.Deserialize<List<NotificationDTO>>(json, options);
        }
        catch (Exception e)
        {
            Console.WriteLine($"Error: {e.Message}");
            throw new Exception("Error getting notifications");
        }
    }

}