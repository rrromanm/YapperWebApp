using DTOs;
using Microsoft.AspNetCore.Mvc;

namespace BlazorApp.Services;

public interface IUserServices
{
    public Task<IActionResult> CreateUser(CreateUserDTO message);
}