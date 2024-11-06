using App.LogicInterfaces;
using DTOs.Models;
using DTOs.User;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class CategoriesController : ControllerBase
{
    private readonly ICategoryLogic _categoryLogic;
    
    public CategoriesController(ICategoryLogic categoryLogic)
    {
        _categoryLogic = categoryLogic;
    }
    
    [HttpPost]
    public async Task<ActionResult> CreateCategory(CreateCategoryDTO dto)
    {
        try
        {
            await _categoryLogic.CreateCategoryAsync(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpPut]
    public async Task<ActionResult> UpdateCategory([FromBody] UpdateCategoryDTO category)
    {
        try
        {
            await _categoryLogic.UpdateCategoryAsync(category);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpDelete("{id:int}")]
    public async Task<ActionResult> DeleteCategory([FromRoute] int id)
    {
        try
        {
            await _categoryLogic.DeleteCategoryAsync(id);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("{id:int}")]
    public async Task<ActionResult> GetCategoryById([FromRoute] int id)
    {
        try
        {
            var result = await _categoryLogic.GetCategoryById(id);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet]
    public async Task<ActionResult> GetAllCategories()
    {
        try
        {
            var result = await _categoryLogic.GetAllCategoriesAsync();
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet("Name/{name}")]
    public async Task<ActionResult> GetCategoryByName([FromRoute] string name)
    {
        try
        {
            var result = await _categoryLogic.GetCategoryByName(name);
            return Ok(result);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

}