using System.Net.Http.Json;
using DTOs.DTOs;
using HttpClients.ClientInterfaces;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;
using JsonSerializerOptions = System.Text.Json.JsonSerializerOptions;

namespace HttpClients.ClientImplementations;

public class ChatHttpClient : IChatService
{
    private readonly HttpClient _client;
    
    public ChatHttpClient(HttpClient client)
    {
        _client = client;
    }
    
    public async Task SendMessage(SendMessageDTO dto)
    {
        try
        {
            HttpResponseMessage response = await _client.PostAsJsonAsync("/Chat", dto);
            if (!response.IsSuccessStatusCode)
            {
                string e = await response.Content.ReadAsStringAsync();
                throw new Exception(e);
            }
        }
        catch (Exception e)
        {
            throw new Exception(e.Message);
        }
    }

    public async Task<List<MessageDTO>> GetAllMessages()
    {
        try
        {
            HttpResponseMessage response = await _client.GetAsync("/Chat");
            string content = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(content);
            }
            
            return JsonSerializer.Deserialize<List<MessageDTO>>(content, new JsonSerializerOptions{ PropertyNameCaseInsensitive = true});
        }
        catch (Exception e)
        {
            throw new Exception(e.Message);
        }
    }

    public async Task<List<MessageDTO>> GetMessages(int sender, int receiver)
    {
        try
        {
            HttpResponseMessage response = await _client.GetAsync($"/Chat/{sender}/{receiver}");
            string content = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(content);
            }
            
            return JsonSerializer.Deserialize<List<MessageDTO>>(content, new JsonSerializerOptions{ PropertyNameCaseInsensitive = true});
        } catch (Exception e)
        {
            throw new Exception(e.Message);
        }
    }
}