using System.Net.Http.Json;
using System.Text;
using System.Text.Json;
using DTOs.DTOs.Notification;
using HttpClients.ClientInterfaces;
using RabbitMQ.Client;

namespace HttpClients.ClientImplementations;

public class NotificationHttpClient : INotificationService
{
    private readonly HttpClient _client;
    
    public NotificationHttpClient(HttpClient client)
    {
        _client = client;
    }
    
    public async Task SendNotificationAsync(NotificationDTO dto)
    {
        await PublishToRabbitMQ(dto);
        await _client.PostAsJsonAsync("/Notification", dto);
    }

    public async Task<List<NotificationDTO>> GetNotificationsAsync(int userId)
    {
       HttpResponseMessage response = await _client.GetAsync($"/Notification/{userId}");
         if (!response.IsSuccessStatusCode)
         {
              string e = await response.Content.ReadAsStringAsync();
              throw new Exception(e);
         }
         string content = await response.Content.ReadAsStringAsync();
         return JsonSerializer.Deserialize<List<NotificationDTO>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
    }
    
    public async Task PublishToRabbitMQ(NotificationDTO dto)
    {
        var factory = new ConnectionFactory { HostName = "localhost" };

        using var connection = await factory.CreateConnectionAsync();
        using var channel = await connection.CreateChannelAsync();
        
        await channel.ExchangeDeclareAsync(exchange: "notifications.exchange", type: ExchangeType.Topic);
        
        string routingKey = $"notifications.{dto.UserId}";
        var messageBody = JsonSerializer.Serialize(dto);
        var body = Encoding.UTF8.GetBytes(messageBody);
        
        await channel.BasicPublishAsync(exchange: "notifications.exchange", routingKey: routingKey, body: body);

        Console.WriteLine($"Notification published to RabbitMQ: {messageBody}");
    }
}