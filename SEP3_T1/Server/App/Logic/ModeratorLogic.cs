using App.LogicInterfaces;
using DTOs.Models;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic;

public class ModeratorLogic : IModeratorLogic
{
    private ModeratorService.ModeratorServiceClient client;
    
    public ModeratorLogic(GRPCService service)
    {
        GrpcChannel channel = service.Channel;
        client = new ModeratorService.ModeratorServiceClient(channel);
    }
    
    
    public async Task<Moderator> GetModeratorByUsernameAsync(string username)
    {
        var response = await client.GetModeratorByUserNameAsync(new GetModeratorRequest
        {
            Username = username
        });
        
        Moderator moderator = new Moderator
        {
            Id = response.Id,
            Username = response.Username,
            Password = response.Password
        };
        
        return moderator;
    }
}