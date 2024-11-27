using App.LogicInterfaces;
using DTOs.DTOs.Auth;
using DTOs.User;
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
    
    [HttpPost("/register")]
    public async Task<IResult> RegisterAsync(CreateUserDTO dto)
    {
        try
        {
            var user = await _logic.CreateSMUser(dto);
            var user1 = await _logic.GetByUsernameAsync(user.Username); // spaghetti ahh code
            var userDto = new UserDTO
            {
                Id = user1.Id,
                Username = user1.Username,
                Nickname = user1.Nickname,
                Password = user1.Password,
                Email = user1.Email
            };
            return Results.Ok(userDto);
        }
        catch (Exception e)
        {
            return Results.BadRequest(e.Message);
        }
    }



}