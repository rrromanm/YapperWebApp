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
    
    [HttpGet("{followerId:int}/IsFollowing/{followedId:int}")]
    public async Task<ActionResult<bool>> IsFollowing(int followerId, int followedId)
    {
        try
        {
            var isFollowing = await _smUserLogic.IsFollowing(followerId, followedId);
            return Ok(isFollowing);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("{id:int}/Followers")]
    public async Task<ActionResult> GetFollowers([FromRoute] int id)
    {
        try
        {
            var followers = await _smUserLogic.GetFollowers(id);
            return Ok(followers);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("{id:int}/Following")]
    public async Task<ActionResult> GetFollowing([FromRoute] int id)
    {
        try
        {
            var following = await _smUserLogic.GetFollowing(id);
            return Ok(following);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("{id:int}/Random")]
    public async Task<ActionResult> GetThreeRandomUsers([FromRoute] int id)
    {
        try
        {
            var users = await _smUserLogic.GetThreeRandomUsers(id);
            return Ok(users);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("search/{searchText}")]
    public async Task<ActionResult> GetUsersBySearch([FromRoute] string searchText)
    {
        try
        {
            var users = await _smUserLogic.GetUsersBySearch(searchText);
            return Ok(users);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}