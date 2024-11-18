using App.LogicInterfaces;
using DTOs.User;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic;

public class SMUserLogic : ISMUserLogic
{
   private SMUserService.SMUserServiceClient client;

   public SMUserLogic(GRPCService service)
   {
       GrpcChannel channel = service.Channel;
       client = new SMUserService.SMUserServiceClient(channel);
   }
    
    public async Task CreateSMUser(CreateUserDTO dto)
    {
        try
        {
            await client.CreateUserAsync(new CreateSMUserRequest
            {
                Username = dto.Username,
                Password = dto.Password,
                Email = dto.Email,
                Nickname = dto.Nickname
            });

        }catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error creating user");
        }
    }

    public async Task UpdateSMUser(UpdateUserDTO dto)
    {
        
    }

    public async Task DeleteUser(int userId)
    {
        try
        {
            await client.DeleteUserAsync(new DeleteSMUserRequest
            {
                Id = userId
            });
        }catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error deleting user");
        }
    }
}