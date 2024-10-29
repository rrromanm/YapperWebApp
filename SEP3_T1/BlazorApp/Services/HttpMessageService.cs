using System.Net.Http;
using System.Threading.Tasks;
using API;
using Microsoft.AspNetCore.Mvc;
using Grpc.Net.Client;
using GrpcService.Protos;

namespace BlazorApp.Services
{
    public class HttpMessageService : IMessageService
    {
        private readonly GrpcChannel _channel;

        public HttpMessageService(HttpClient client)
        {
            _channel = GrpcChannel.ForAddress("http://localhost:8080", new GrpcChannelOptions
            {
                HttpClient = client
            });
        }

        public async Task<IActionResult> SendMessage(SendMessage message)
        {
            var grpcClient = new TextConverter.TextConverterClient(_channel);
            var reply = await grpcClient.ToUpperAsync(new RequestText { InputText = message.MessageContent });

            return new OkObjectResult(new { reply.OutputText });
        }
    }
}