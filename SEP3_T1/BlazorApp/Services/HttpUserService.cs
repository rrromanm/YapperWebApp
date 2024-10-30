using DTOs;
using Grpc.Net.Client;
using Microsoft.AspNetCore.Mvc;
using GrpcService.Protos;
using Microsoft.AspNetCore.Identity.Data;
using Sep3.Proto;

namespace BlazorApp.Services;

public class HttpUserService : IUserServices
{
    private readonly GrpcChannel _channel;
    
    public HttpUserService(HttpClient client)
    {
        _channel = GrpcChannel.ForAddress("http://localhost:8080", new GrpcChannelOptions
        {
            HttpClient = client
        });
    }
    
    public async Task<IActionResult> CreateUser(CreateUserDTO user)
    {
        var grpcClient = new UserService.UserServiceClient(_channel);
        var reply = await grpcClient.CreateUserAsync(new CreateUserRequest
        {
            User = new SocialMediaUser
            {
                Username = user.Username,
                Password = user.Password,
                Email = user.Email
            }
        });

        return new OkObjectResult(new { reply.User });
    }
}