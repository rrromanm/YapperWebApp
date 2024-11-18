using App.LogicInterfaces;
using DTOs.User;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class SMUController : ControllerBase
{
    private readonly ISMUserLogic _smUserLogic;
    
    public SMUController(ISMUserLogic smUserLogic)
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
            return Ok();
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
}