using DTOs.DTOs.CategoryRequest;
using DTOs.Models;

namespace App.LogicInterfaces;

public interface ICategoryRequestLogic
{
    Task CreateCategoryRequest(CreateCategoryRequestDTO dto);
    Task DeleteCategoryRequest(int id);
    Task<CategoryRequest> GetCategoryRequestById(int id);
    Task<List<CategoryRequest>> GetAllCategoryRequests();
    Task<List<CategoryRequest>> GetCategoryRequestsByName(string name);
    Task ApproveCategoryRequest(string categoryName, int addedBy);
    Task DisapproveCategoryRequest(string categoryName);
}