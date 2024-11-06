using App.LogicInterfaces;
using DTOs.DTOs;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;
[ApiController]
[Route("[controller]")]
public class CommentController : ControllerBase
{
    private readonly ICommentLogic _commentLogic;
    
    public CommentController(ICommentLogic commentLogic)
    {
        _commentLogic = commentLogic;
    }
    [HttpPost]
    public async Task<ActionResult> CreateComment(CreateCommentDTO dto)
    {
        try
        {
            await _commentLogic.CreateComment(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpPatch]
    public async Task<ActionResult> UpdateComment(UpdateCommentDTO dto)
    {
        try
        {
            await _commentLogic.UpdateComment(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}