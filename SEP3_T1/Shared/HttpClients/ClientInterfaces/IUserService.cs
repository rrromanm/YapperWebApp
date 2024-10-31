using DTOs.User;

namespace HttpClients.ClientInterfaces;
using Microsoft.AspNetCore.Mvc;

public interface IUserServices
{
    public Task<IActionResult> CreateUser(CreateUserDTO message);
}