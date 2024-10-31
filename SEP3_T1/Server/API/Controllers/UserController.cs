using Grpc.Net.Client;
using GrpcService.Protos;
using Microsoft.AspNetCore.Mvc;
using Sep3.Proto;

namespace API.Controllers;


[ApiController]
[Route("[controller]")]
public class UserController : ControllerBase
{
    [HttpPost(Name = "CreateUser")]
    public async Task<IActionResult> createUser([FromQuery] string username, [FromQuery] string password, [FromQuery] string email)
    {
        using var channel = GrpcChannel.ForAddress("http://localhost:8080");
        var client = new UserService.UserServiceClient(channel);
        
        var user = new SocialMediaUser
        {
            Email = email,
            Username = username,
            Password = password
        };
        
        var request = new CreateUserRequest { User = user };
        var reply = await client.CreateUserAsync(request);
        
        return Ok(new { reply.User });
    }
}