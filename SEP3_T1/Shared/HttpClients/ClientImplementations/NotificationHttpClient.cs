using System.Net.Http.Json;
using System.Text.Json;
using DTOs.DTOs.Notification;
using HttpClients.ClientInterfaces;

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
        HttpResponseMessage response = await _client.PostAsJsonAsync("/Notification", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
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
}