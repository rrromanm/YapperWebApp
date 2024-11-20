using DTOs.Models;
using DTOs.User;
using Microsoft.AspNetCore.Mvc;

namespace App.LogicInterfaces;

public interface ISMUserLogic
{
    Task CreateSMUser(CreateUserDTO dto);
    Task UpdateSMUser(UpdateUserDTO dto);
    Task DeleteUser(int accountId);
    Task<List<User>> GetAllUsers();
    Task<User> GetByUsernameAsync(string username);
    Task<User> GetByIdAsync(int userId); // Add this method
}