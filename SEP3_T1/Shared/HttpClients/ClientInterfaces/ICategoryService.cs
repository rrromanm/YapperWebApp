using DTOs.Models;
using DTOs.User;

namespace HttpClients.ClientInterfaces;

public interface ICategoryService
{
    Task CreateCategory(CreateCategoryDTO dto);
    Task UpdateCategory(UpdateCategoryDTO dto);
    Task DeleteCategory(int id);
    Task<Category> GetCategoryById(int id);
    Task<List<Category>> GetAllCategories();
    Task<Category> GetCategoryByName(string name);
}