using DTOs.Models;
using DTOs.User;
using Microsoft.AspNetCore.Mvc;

namespace App.LogicInterfaces;

public interface ISMUserLogic
{
    Task<User> CreateSMUser(CreateUserDTO dto);
    Task UpdateEmail(UpdateUserDTO dto);
    Task UpdatePassword(UpdateUserDTO dto);
    Task UpdateNickname(UpdateUserDTO dto);
    Task DeleteUser(int accountId);
    Task<List<User>> GetAllUsers();
    Task<User> GetByUsernameAsync(string username);
    Task<User> GetByIdAsync(int userId); // Add this method
    Task FollowUser(int followerId, int followedId);
    Task UnfollowUser(int followerId, int followedId);
    Task<List<FollowersDTO>> GetFollowers(int userId);
    Task<List<FollowersDTO>> GetFollowing(int userId);
    Task<bool> IsFollowing(int followerId, int followedId);
    Task<List<FollowersDTO>> GetThreeRandomUsers(int id);
    Task<List<FollowersDTO>> GetUsersBySearch(string searchText);
}