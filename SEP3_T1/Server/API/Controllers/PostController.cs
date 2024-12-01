using System.Xml;
using App.Logic;
using App.LogicInterfaces;
using DTOs.DTOs;
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
            
            Console.WriteLine($"Post created by user {dto.UserId}");
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
            if(!string.IsNullOrEmpty(dto.Body) && !string.IsNullOrEmpty(dto.Title) && dto.CategoryId != 0 && dto.AccountId != 0)
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
    
    [HttpGet("GetPostsByUserId/{userId:int}")]
    public async Task<ActionResult> GetPostsByUserId([FromRoute] int userId)
    {
        try
        {
            var result = await _postLogic.GetPostsByUserId(userId);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("GetPostsByCategoryId/{categoryId:int}")]
    public async Task<ActionResult> GetPostsByCategoryId([FromRoute] int categoryId)
    {
        try
        {
            var result = await _postLogic.GetPostsByCategoryId(categoryId);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("GetLikedPosts/{userId:int}")]
    public async Task<ActionResult> GetLikedPosts([FromRoute] int userId)
    {
        try
        {
            var result = await _postLogic.GetLikedPosts(userId);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpPost("LikePost/{userId:int}/{postId:int}")]
    public async Task<ActionResult> LikePost([FromRoute] int userId, [FromRoute] int postId)
    {
        try
        {
            await _postLogic.LikePost(userId, postId);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpPost("UnlikePost/{userId:int}/{postId:int}")]
    public async Task<ActionResult> UnlikePost([FromRoute] int userId, [FromRoute] int postId)
    {
        try
        {
            await _postLogic.UnlikePost(userId, postId);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}