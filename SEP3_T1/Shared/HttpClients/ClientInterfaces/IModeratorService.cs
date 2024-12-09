using DTOs.Models;

namespace HttpClients.ClientInterfaces;

public interface IModeratorService
{
    Task<Moderator> GetByUsername(string username);
}