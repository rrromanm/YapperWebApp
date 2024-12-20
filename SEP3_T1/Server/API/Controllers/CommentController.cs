﻿using App.LogicInterfaces;
using DTOs.DTOs;
using DTOs.DTOs.Comment;
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
            await _commentLogic.CreateCommentAsync(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpPatch ("update")]
    public async Task<ActionResult> UpdateComment(UpdateCommentDTO dto)
    {
        try
        {
            await _commentLogic.UpdateCommentAsync(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpDelete("{id:int}")]
    public async Task<ActionResult> DeleteComment(int id)
    {
        try
        {
            await _commentLogic.DeleteCommentAsync(id);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpGet("{id:int}")]
    public async Task<ActionResult> GetComment(int id)
    {
        try
        {
            var comment = await _commentLogic.GetCommentAsync(id);
            return Ok(comment);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpGet]
    public async Task<ActionResult> GetAllComments()
    {
        try
        {
            var comments = await _commentLogic.GetAllCommentsAsync();
            return Ok(comments);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpGet("post/{postId:int}")]
    public async Task<ActionResult> GetCommentsByPostId(int postId)
    {
        try
        {
            var comments = await _commentLogic.GetCommentsByPostIdAsync(postId);
            return Ok(comments);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpGet("user/{userId:int}")]
    public async Task<ActionResult> GetCommentsByUserId(int userId)
    {
        try
        {
            var comments = await _commentLogic.GetCommentsByUserIdAsync(userId);
            return Ok(comments);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpPost("like/{commentId:int}/{userId:int}")]
    public async Task<ActionResult> LikeComment(int commentId, int userId)
    {
        try
        {
            await _commentLogic.LikeCommentAsync(commentId, userId);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpPost("unlike/{commentId:int}/{userId:int}")]
    public async Task<ActionResult> UnlikeComment(int commentId, int userId)
    {
        try
        {
            await _commentLogic.UnlikeCommentAsync(commentId, userId);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpGet ("liked/{userId:int}")]
    public async Task<ActionResult> GetAllLikedComments(int userId)
    {
        try
        {
            var comments = await _commentLogic.GetAllLikedCommentsAsync(userId);
            return Ok(comments);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}