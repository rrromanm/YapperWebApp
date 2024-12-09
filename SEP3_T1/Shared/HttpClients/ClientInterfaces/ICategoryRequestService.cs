using DTOs.DTOs.CategoryRequest;

namespace HttpClients.ClientInterfaces;

public interface ICategoryRequestService
{
    Task CreateCategoryRequest(CreateCategoryRequestDTO dto);
    Task DeleteCategoryRequest(int id);
    Task<CategoryRequestDTO> GetCategoryRequestById(int id);
    Task<List<CategoryRequestDTO>> GetAllCategoryRequests();
    Task<List<CategoryRequestDTO>> GetCategoryRequestsByName(string name);
    Task ApproveCategoryRequest(string categoryName, int addedBy);
    Task DisapproveCategoryRequest(string categoryName);
}