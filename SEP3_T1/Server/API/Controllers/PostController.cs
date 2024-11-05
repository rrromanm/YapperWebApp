using App.Logic;
using DTOs.User.PostDTOs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class PostController : ControllerBase
{
    private readonly PostLogic _postLogic;
        
    public PostController(PostLogic postLogic)
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
    
    // [HttpPatch]
    // public async Task<ActionResult> UpdatePost(UpdatePostDTO dto)
    // {
    //     try
    //     {
    //         if(!string.IsNullOrEmpty(dto.Content))
    //             await _postLogic.UpdateContent(dto);
    //         if(!string.IsNullOrEmpty(dto.Title))
    //             await _postLogic.UpdateTitle(dto);
    //         return Ok();
    //     }
    //     catch (Exception e)
    //     {
    //         return BadRequest(e.Message);
    //     }
    // }
    //
    // [HttpDelete]
    // public async Task<ActionResult> DeletePost([FromRoute] int postId)
    // {
    //     try
    //     {
    //         await _postLogic.DeletePost(postId);
    //         return Ok();
    //     }
    //     catch (Exception e)
    //     {
    //         return BadRequest(e.Message);
    //     }
    // }
    //
    // [HttpGet]
    // public async Task<ActionResult> GetPost([FromRoute] int postId)
    // {
    //     try
    //     {
    //         var post = await _postLogic.GetPost(postId);
    //         return Ok(post);
    //     }
    //     catch (Exception e)
    //     {
    //         return BadRequest(e.Message);
    //     }
    // }
}