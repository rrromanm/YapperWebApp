using DTOs.Models;
using DTOs.User;
using Microsoft.AspNetCore.Mvc;

namespace App.LogicInterfaces;

public interface ISMUserLogic
{
    Task<SMUser> CreateSMUser(CreateUserDTO dto);
    Task UpdateEmail(UpdateUserDTO dto);
    Task UpdatePassword(UpdateUserDTO dto);
    Task UpdateNickname(UpdateUserDTO dto);
    Task DeleteUser(int accountId);
    Task<List<SMUser>> GetAllUsers();
    Task<SMUser> GetByUsernameAsync(string username);
    Task<SMUser> GetByIdAsync(int userId); // Add this method
    Task FollowUser(int followerId, int followedId);
    Task UnfollowUser(int followerId, int followedId);
    Task<List<FollowersDTO>> GetFollowers(int userId);
    Task<List<FollowersDTO>> GetFollowing(int userId);
    Task<bool> IsFollowing(int followerId, int followedId);
    Task<List<FollowersDTO>> GetThreeRandomUsers(int id);
}