using System.Text.Json;
using App.LogicInterfaces;
using DTOs.Models;
using DTOs.User;
using Grpc.Net.Client;
using GrpcClient;
using Microsoft.AspNetCore.Mvc;
using SocialMediaUser;

namespace App.Logic;

public class SMUserLogic : ISMUserLogic
{
    private SMUserService.SMUserServiceClient client;

    public SMUserLogic(GRPCService service)
    {
        GrpcChannel channel = service.Channel;
        client = new SMUserService.SMUserServiceClient(channel);
    }

    public async Task<User> CreateSMUser(CreateUserDTO dto)
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
            
            var user = new User
            {
                Username = dto.Username,
                Email = dto.Email,
                Nickname = dto.Nickname,
                Password = dto.Password
            };

            return user;

        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error creating user");
        }
    }

    public async Task UpdateEmail(UpdateUserDTO dto)
    {
        try
        {
            await client.UpdateEmailAsync(new UpdateSMUserEmailRequest
            {
                Id = dto.UserId,
                Email = dto.Email
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error updating email");
        }
    }

    public async Task UpdatePassword(UpdateUserDTO dto)
    {
        try
        {
            await client.UpdatePasswordAsync(new UpdateSMUserPasswordRequest()
            {
                Id = dto.UserId,
                Password = dto.Password
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error updating password");
        }
    }

    public async Task UpdateNickname(UpdateUserDTO dto)
    {
        try
        {
            await client.UpdateNicknameAsync(new UpdateSMUserNicknameRequest()
            {
                Id = dto.UserId,
                Nickname = dto.Nickname
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error updating nickname");
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
        }
        catch (Exception e)
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
            var response = await client.GetByUserNameAsync(new GetSMUserRequest
            {
                Username = userName
            });
            User user = new User(response.Username, response.Password, response.Email, response.Nickname, response.Id);
            return user;
    }

    public async Task<User> GetByIdAsync(int userId)
    {
        try
        {
            var response = await client.GetByIDAsync(new GetSMUserRequest
            {
                Id = userId
            });
            User user = new User(response.Username, response.Password, response.Email, response.Nickname, response.Id);
            return user;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting user by id");
        }
    }

    public async Task FollowUser(int followerId, int followedId)
    {
        try
        {
            await client.FollowUserAsync(new FollowUserRequest
            {
                FollowerId = followerId,
                FollowedId = followedId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error following user");
        }
    }

    public async Task UnfollowUser(int followerId, int followedId)
    {
        try
        {
            await client.UnfollowUserAsync(new UnfollowUserRequest
            {
                FollowerId = followerId,
                FollowedId = followedId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error unfollowing user");
        }
    }
}