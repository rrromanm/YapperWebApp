using App.LogicInterfaces;
using DTOs.Models;
using DTOs.User;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class SMUserController : ControllerBase
{
    private readonly ISMUserLogic _smUserLogic;
    
    public SMUserController(ISMUserLogic smUserLogic)
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
            if (!string.IsNullOrEmpty(dto.Email))
            {
                await _smUserLogic.UpdateEmail(dto);
            }

            if (!string.IsNullOrEmpty(dto.Password))
            {
                await _smUserLogic.UpdatePassword(dto);
            }
            
            if (!string.IsNullOrEmpty(dto.Nickname))
            {
                await _smUserLogic.UpdateNickname(dto);
            }

            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet]
    public async Task<ActionResult> GetUsersAsync()
    {
        try
        {
            var users = await _smUserLogic.GetAllUsers();
            return Ok(users);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("{id:int}")]
    public async Task<ActionResult> GetByIdAsync([FromRoute] int id)
    {
        try
        {
            var user = await _smUserLogic.GetByIdAsync(id);
            return Ok(user);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("{username}")]
    public async Task<ActionResult> GetByUsernameAsync([FromRoute] string username)
    {
        try
        {
            var user = await _smUserLogic.GetByUsernameAsync(username);
            return Ok(user);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpDelete("{id:int}")]
    public async Task<ActionResult> DeleteUser([FromRoute] int id)
    {
        try
        {
            await _smUserLogic.DeleteUser(id);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpPost("{followerId:int}/Follow/{followedId:int}")]
    public async Task<ActionResult> FollowUser([FromRoute] int followerId, [FromRoute] int followedId)
    {
        try
        {
            await _smUserLogic.FollowUser(followerId, followedId);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpPost("{followerId:int}/Unfollow/{followedId:int}")]
    public async Task<ActionResult> UnfollowUser([FromRoute] int followerId, [FromRoute] int followedId)
    {
        try
        {
            await _smUserLogic.UnfollowUser(followerId, followedId);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    
}