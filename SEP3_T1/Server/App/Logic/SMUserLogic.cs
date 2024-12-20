using System.Text.Json;
using App.LogicInterfaces;
using DTOs.Models;
using DTOs.User;
using Grpc.Core;
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

    public async Task<SMUser> CreateSMUser(CreateUserDTO dto)
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

            var user = new SMUser
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

    public async Task<List<SMUser>> GetAllUsers()
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
            List<SMUser> users = JsonSerializer.Deserialize<List<SMUser>>(json, options);
            return users;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting all users");
        }
    }

    public async Task<SMUser> GetByUsernameAsync(string userName)
    {
        var response = await client.GetByUserNameAsync(new GetSMUserRequest
        {
            Username = userName
        });
        SMUser smUser = new SMUser(response.Username, response.Password, response.Email, response.Nickname, response.Id,
            response.FollowersCount, response.FollowingCount);
        return smUser;
    }

    public async Task<SMUser> GetByIdAsync(int userId)
    {
        try
        {
            var response = await client.GetByIDAsync(new GetSMUserRequest
            {
                Id = userId
            });
            SMUser smUser = new SMUser(response.Username, response.Password, response.Email, response.Nickname, response.Id,
                response.FollowersCount, response.FollowingCount);
            return smUser;
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

    public async Task<List<FollowersDTO>> GetFollowers(int userId)
    {
        try
        {
            GetFollowersResponse response = await client.GetFollowersAsync(new GetFollowersRequest
            {
                Id = userId
            });
            
            if (string.IsNullOrWhiteSpace(response.List))
            {
                return new List<FollowersDTO>();
            }
            
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<FollowersDTO> followers = JsonSerializer.Deserialize<List<FollowersDTO>>(response.List, options);

            return followers;
        }
        catch (Exception e)
        {
            Console.WriteLine($"Error fetching following list: {e.Message}");
            throw;
        }
    }

    public async Task<List<FollowersDTO>> GetFollowing(int userId)
    {
        try
        {
            GetFollowersResponse response = await client.GetFollowingAsync(new GetFollowersRequest
            {
                Id = userId
            });
            
            if (string.IsNullOrWhiteSpace(response.List))
            {
                return new List<FollowersDTO>();
            }
            
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<FollowersDTO> following = JsonSerializer.Deserialize<List<FollowersDTO>>(response.List, options);

            return following;
        }
        catch (Exception e)
        {
            Console.WriteLine($"Error fetching following list: {e.Message}");
            throw;
        }
    }

    public async Task<bool> IsFollowing(int followerId, int followedId)
    {
        try
        {
            var response = await client.IsFollowingAsync(new IsFollowingRequest
            {
                FollowerId = followerId,
                FollowedId = followedId
            });
            
            return response.IsFollowing;
        
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
        
    }

    public async Task<List<FollowersDTO>> GetThreeRandomUsers(int id)
    {
        try
        {
            GetThreeRandomUsersResponse response = await client.GetThreeRandomUsersAsync(new GetThreeRandomUsersRequest
            {
                UserId = id
            });
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            
            List<FollowersDTO> users = JsonSerializer.Deserialize<List<FollowersDTO>>(json, options);
            
            return users;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<FollowersDTO>> GetUsersBySearch(string searchText)
    {
        try
        {
            GetUsersBySearchResponse response = await client.GetUsersBySearchAsync(new GetUsersBySearchRequest
            {
                SearchText = searchText
            });
            
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            
            List<FollowersDTO> users = JsonSerializer.Deserialize<List<FollowersDTO>>(json, options);

            return users;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}
