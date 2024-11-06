using DTOs.Models;
using DTOs.User;

namespace App.LogicInterfaces;

public interface ICategoryLogic
{
    Task CreateCategoryAsync(CreateCategoryDTO dto);
    Task UpdateCategoryAsync(UpdateCategoryDTO dto);
    Task DeleteCategoryAsync(int id);
    Task<Category> GetCategoryById(int id);
    Task<List<Category>> GetAllCategoriesAsync();
    Task<Category> GetCategoryByName(string name);
    
}