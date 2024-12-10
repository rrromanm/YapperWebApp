using App.LogicInterfaces;
using DTOs.DTOs.Auth;
using DTOs.User;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class AuthController : ControllerBase
{
    private readonly ISMUserLogic smUserLogic;
    private readonly IModeratorLogic moderatorLogic;
    
    public AuthController(ISMUserLogic logic, IModeratorLogic moderatorLogic)
    {
        smUserLogic = logic;
        this.moderatorLogic = moderatorLogic;
    }
    
    [HttpPost("/login")]
    public async Task<IResult> LoginAsync([FromBody] LoginRequestDTO requestDto)
    {
        var user = await smUserLogic.GetByUsernameAsync(requestDto.Username);
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
    
    [HttpPost("/moderatorLogin")]
    public async Task<IResult> ModeratorLoginAsync([FromBody] LoginRequestDTO requestDto)
    {
        var moderator = await moderatorLogic.GetModeratorByUsernameAsync(requestDto.Username);
        if (moderator == null)
        {
            return Results.NotFound($"Moderator with username '{requestDto.Username}' not found.");
        }
        
        if (moderator.Password != requestDto.Password)
        {
            return Results.Unauthorized();
        }
        
        return Results.Ok(moderator);
    }

    
    [HttpPost("/register")]
    public async Task<IResult> RegisterAsync(CreateUserDTO dto)
    {
        try
        {
            var user = await smUserLogic.CreateSMUser(dto);
            var user1 = await smUserLogic.GetByUsernameAsync(user.Username); // spaghetti ahh code
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