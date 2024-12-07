using DTOs.Models;

namespace App.LogicInterfaces;

public interface IModeratorLogic
{
    Task<Moderator> GetModeratorByUsernameAsync(string username);
}