using App.LogicInterfaces;
using DTOs.DTOs.CategoryRequest;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;
[ApiController]
[Route("[controller]")]

public class CategoryRequestController : ControllerBase
{
    private readonly ICategoryRequestLogic _categoryRequestLogic;
    
    public CategoryRequestController(ICategoryRequestLogic categoryRequestLogic)
    {
        _categoryRequestLogic = categoryRequestLogic;
    }
    
    [HttpPost]
    public async Task<ActionResult> CreateCategoryRequest(CreateCategoryRequestDTO dto)
    {
        try
        {
            await _categoryRequestLogic.CreateCategoryRequest(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpDelete("{id:int}")]
    public async Task<ActionResult> DeleteCategoryRequest(int id)
    {
        try
        {
            await _categoryRequestLogic.DeleteCategoryRequest(id);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("{id:int}")]
    public async Task<ActionResult> GetCategoryRequest(int id)
    {
        try
        {
            var result = await _categoryRequestLogic.GetCategoryRequestById(id);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpGet]
    public async Task<ActionResult> GetAllCategoryRequests()
    {
        try
        {
            var result =await _categoryRequestLogic.GetAllCategoryRequests();
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpGet("{name}")]
    public async Task<ActionResult> GetCategoryRequestsByName(string name)
    {
        try
        {
            var result = await _categoryRequestLogic.GetCategoryRequestsByName(name);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpPost("{categoryName}/Approve{addedBy:int}")]
    public async Task<ActionResult> ApproveCategoryRequest(string categoryName, int addedBy)
    {
        try
        {
            await _categoryRequestLogic.ApproveCategoryRequest(categoryName, addedBy);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    [HttpPost("{categoryName}/Disapprove")]
    public async Task<ActionResult> DisapproveCategoryRequest(string categoryName)
    {
        try
        {
            await _categoryRequestLogic.DisapproveCategoryRequest(categoryName);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

}