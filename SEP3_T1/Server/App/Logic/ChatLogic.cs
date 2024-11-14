using System.Text.Json;
using App.LogicInterfaces;
using DTOs.DTOs;
using Grpc.Core;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic
{
    public class ChatLogic : IChatLogic
    {
        private ChatService.ChatServiceClient _client;

        public ChatLogic(GRPCService service)
        {
            GrpcChannel channel = service.Channel;
            _client = new ChatService.ChatServiceClient(channel);
        }

        public async Task SendMessageAsync(SendMessageDTO dto)
        {
            try
            {
                await _client.SendMessageAsync(new SendMessageRequest
                {
                    Message = dto.Message,
                    SenderId = dto.SenderId,
                    ReceiverId = dto.ReceiverId
                });
            }
            catch (RpcException rpcEx)
            {
                Console.WriteLine($"gRPC Error: {rpcEx.Status.StatusCode}, Detail: {rpcEx.Status.Detail}");
                throw new Exception("Error sending message", rpcEx);
            }
        }

        public async Task<List<MessageDTO>> GetMessagesAsync(int senderId, int receiverId)
        {
            try
            {
                GetMessagesResponse response = await _client.GetMessagesAsync(new GetMessagesRequest
                {
                    SenderId = senderId,
                    ReceiverId = receiverId
                });

                List<MessageDTO> messages = new List<MessageDTO>();
                foreach (var message in response.Messages)
                {
                    messages.Add(new MessageDTO(message.MessageId, message.Message, message.SenderId, message.ReceiverId, message.Timestamp));
                }
                return messages;
            }
            catch (RpcException rpcEx)
            {
                Console.WriteLine($"gRPC Error: {rpcEx.Status.StatusCode}, Detail: {rpcEx.Status.Detail}");
                throw new Exception("Error getting messages", rpcEx);
            }
        }

        public async Task<List<MessageDTO>> GetAllMessagesAsync()
        {
            try
            {
                GetAllMessagesResponse response = await _client.GetAllMessagesAsync(new EmptyMessageRequest());
                string json = response.List;
                JsonSerializerOptions options = new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                };
                List<MessageDTO> messages = JsonSerializer.Deserialize<List<MessageDTO>>(json, options);
                return messages;
            }
            catch (RpcException rpcEx)
            {
                Console.WriteLine($"gRPC Error: {rpcEx.Status.StatusCode}, Detail: {rpcEx.Status.Detail}");
                throw new Exception("Error getting all messages", rpcEx);
            }
        }
    }
}