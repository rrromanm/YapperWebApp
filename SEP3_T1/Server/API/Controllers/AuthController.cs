﻿using App.LogicInterfaces;
using DTOs.DTOs.Auth;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class AuthController : ControllerBase
{
    private readonly ISMUserLogic _logic;
    
    public AuthController(ISMUserLogic logic)
    {
        _logic = logic;
    }
    
    [HttpPost("/login")]
    public async Task<IResult> LoginAsync([FromBody] LoginRequestDTO requestDto)
    {
        var user = await _logic.GetByUsernameAsync(requestDto.Username);
        if (user == null)
        {
            return Results.NotFound($"User with username '{requestDto.Username}' not found.");
        }
    
        if (user.Password != requestDto.Password)
        {
            return Results.Unauthorized();
        }
    
        return Results.Ok(user);
    }

}