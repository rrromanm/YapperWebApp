using System.Net.Http.Json;
using DTOs.DTOs;
using HttpClients.ClientInterfaces;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;
using JsonSerializerOptions = System.Text.Json.JsonSerializerOptions;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Text.Json;

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
        await PublishToRabbitMQ(dto);
        await _client.PostAsJsonAsync("/Chat", dto); // Existing REST API call
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

    public async Task PublishToRabbitMQ(SendMessageDTO dto)
    {
        // Setting up RabbitMQ connection
        var factory = new ConnectionFactory { HostName = "localhost" };

        using var connection = await factory.CreateConnectionAsync();
        using var channel = await connection.CreateChannelAsync();

        // Declaraing exchange between sender and receiver
        await channel.ExchangeDeclareAsync(exchange: "chat.exchange", type: ExchangeType.Topic);

        // Preparing messages to be sent
        string routingKey = $"chat.{dto.ReceiverId}";
        var body = Encoding.UTF8.GetBytes(JsonSerializer.Serialize(dto));

        // Publishing messages to RabbitMQ
        await channel.BasicPublishAsync(exchange: "chat.exchange", routingKey: routingKey, body: body);

        Console.WriteLine($"Message published to RabbitMQ: {dto.Message}");
    }


}