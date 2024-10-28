using Grpc.Net.Client;
using GrpcService.Protos;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class SendMessageController : ControllerBase
{
    [HttpPost(Name = "SendMessage")]
    public async Task<IActionResult> SendMessage([FromBody] SendMessage message)
    {
        using var channel = GrpcChannel.ForAddress("http://localhost:8080");
        var client = new TextConverter.TextConverterClient(channel);
        var reply = await client.ToUpperAsync(new RequestText { InputText = message.MessageContent });
        return Ok(new { reply.OutputText });
    }
}