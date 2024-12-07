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
        try
        {
            var moderator = await moderatorLogic.GetModeratorByUsernameAsync(requestDto.Username);
            if (moderator != null && moderator.Password == requestDto.Password)
            {
                return Results.Ok(new { user = moderator, role = "Admin" });
            }
            
            
            var smUser = await smUserLogic.GetByUsernameAsync(requestDto.Username);
            if (smUser != null && smUser.Password == requestDto.Password)
            {
                return Results.Ok(new { user = smUser, role = "User" });
            }

            return Results.NotFound($"User or Admin with username '{requestDto.Username}' not found.");
        }
        catch (Exception ex)
        {
            return Results.Problem("An error occurred while processing the request.", statusCode: 500);
        }
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