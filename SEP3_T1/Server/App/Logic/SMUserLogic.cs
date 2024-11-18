using System.Text.Json;
using App.LogicInterfaces;
using DTOs.Models;
using DTOs.User;
using Grpc.Net.Client;
using GrpcClient;
using Microsoft.AspNetCore.Mvc;

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

    public async Task<List<User>> GetAllUsers()
    {
        try
        {
            GetAllUsersResponse response = await client.GetAllSMUsersAsync(new GetAllUsersRequest());
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<User> users = JsonSerializer.Deserialize<List<User>>(json, options);
            return users;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting all users");
        }
    }

    public async Task<User> GetByUsernameAsync(string userName)
    {
        try
        {
            var response = await client.GetByUserNameAsyncAsync(new GetSMUserRequest
            {
                Username = userName
            });
            User user = new User( response.Username, response.Password, response.Email, response.Nickname, response.Id);
            return user;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting user by username");
        }
    }

    public Task<User> GetByIdAsync(int userId)
    {
        throw new NotImplementedException();
    }

    // public async Task<User> GetByIdAsync(int userId)
    // {
    //     try
    //     {
    //         var response = await client.GetUserByIdAsync(new GetUserByIdRequest { Id = userId });
    //         return new User
    //         {
    //             Id = response.Id,
    //             Username = response.Username,
    //             Email = response.Email,
    //             // Map other properties as needed
    //         };
    //     }
    //     catch (Exception e)
    //     {
    //         Console.WriteLine(e);
    //         throw new Exception("Error getting user by ID");
    //     }
    // }
}