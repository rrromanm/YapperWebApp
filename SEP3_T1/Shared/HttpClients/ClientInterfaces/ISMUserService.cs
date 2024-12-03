using DTOs.Models;
using DTOs.User;

namespace HttpClients.ClientInterfaces;
using Microsoft.AspNetCore.Mvc;

public interface ISMUserService
{
    Task CreateSMUser(CreateUserDTO dto);
    Task UpdateSMUser(UpdateUserDTO dto);
    Task DeleteSMUser(int accountId);
    Task<User> GetByUsername(string username);
    Task<User> GetByUserId(int userId);
    Task FollowUser(int followerId, int followedId);
    Task UnfollowUser(int followerId, int followedId);
    Task<List<FollowersDTO>> GetFollowers(int userId);
    Task<List<FollowersDTO>> GetFollowing(int userId);
    Task<List<User>> GetAllUsers();
    Task<bool> IsFollowing(int followerId, int followedId);
    Task<List<FollowersDTO>> GetThreeRandomUsers();
}