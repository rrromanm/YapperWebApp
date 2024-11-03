using App.LogicInterfaces;
using DTOs.User;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class SocialMediaUserController : ControllerBase
{
    private readonly ISMUserLogic _smUserLogic;
    
    public SocialMediaUserController(ISMUserLogic smUserLogic)
    {
        _smUserLogic = smUserLogic;
    }
    
    [HttpPost, AllowAnonymous]
    public async Task<ActionResult> CreateSMUser(CreateUserDTO dto)
    {
        try
        {
            await _smUserLogic.CreateSMUser(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpPatch]
    public async Task<ActionResult> UpdateAsync(UpdateUserDTO dto)
    {
        try
        {
            if(!string.IsNullOrEmpty(dto.Email))
                await _smUserLogic.UpdateEmail(dto);
            if(!string.IsNullOrEmpty(dto.Nickname))
                await _smUserLogic.UpdateNickname(dto);
            if(!string.IsNullOrEmpty(dto.Password))
                await _smUserLogic.UpdatePassword(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    
    
    [HttpDelete]
    public async Task<ActionResult> DeleteUser([FromRoute] int accountId)
    {
        try
        {
            await _smUserLogic.DeleteUser(accountId);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}