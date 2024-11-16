using App.Logic;
using App.LogicInterfaces;
using DTOs.User.PostDTOs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class PostController : ControllerBase
{
    private readonly IPostLogic _postLogic;
        
    public PostController(IPostLogic postLogic)
    {
        _postLogic = postLogic;
    }
    
    
    [HttpPost]
    public async Task<ActionResult> CreatePost(CreatePostDTO dto)
    {
        try
        {
            await _postLogic.CreatePost(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpPatch]
    public async Task<ActionResult> UpdatePost(UpdatePostDTO dto)
    {
        try
        {
            if(!string.IsNullOrEmpty(dto.Content) && !string.IsNullOrEmpty(dto.Title) && dto.CategoryId != 0 && dto.AccountId != 0)
                await _postLogic.UpdatePost(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpDelete("{id:int}")]
    public async Task<ActionResult> DeletePost([FromRoute] int id)
    {
        try
        {
            await _postLogic.DeletePost(id);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("{id:int}")]
    public async Task<ActionResult> GetPostById([FromRoute] int id)
    {
        try
        {
            var result = await _postLogic.GetPost(id);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet]
    public async Task<ActionResult> GetAllPosts()
    {
        try
        {
            var result = await _postLogic.GetAllPosts();
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("GetFollowingPosts/{userId:int}")]
    public async Task<ActionResult> GetFollowingPosts([FromRoute] int userId)
    {
        try
        {
            var result = await _postLogic.GetFollowingPosts(userId);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}