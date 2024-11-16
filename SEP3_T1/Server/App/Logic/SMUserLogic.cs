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

    public async Task UpdateEmail(UpdateUserDTO dto)
    {
        if(dto.Email == null)
            throw new Exception("Email cannot be empty");
        try
        {
            await client.UpdateEmailAsync(new UpdateSMUserEmailRequest
                {
                Id = dto.UserId,
                Email = dto.Email
            });
        }catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error updating email");
        }
    }

    public async Task UpdateNickname(UpdateUserDTO dto)
    {
        if (dto.Nickname == null || dto.Nickname.Length > 50)
            throw new Exception("Nickname cannot be empty or longer than 50 characters");
        try
        {
            await client.UpdateNicknameAsync(new UpdateSMUserNicknameRequest
            {
                Id = dto.UserId,
                Nickname = dto.Nickname
            });
        }catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error updating nickname");
        }
    }

    public async Task UpdatePassword(UpdateUserDTO dto)
    {
        if(dto.Password == null || dto.Password.Length < 8)
            throw new Exception("Password cannot be empty or shorter than 8 characters");
        try
        {
             await client.UpdatePasswordAsync(new UpdateSMUserPasswordRequest
            {
                Id = dto.UserId,
                Password = dto.Password
            });
        }catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error updating password");
        }
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