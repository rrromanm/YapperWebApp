using System.Text.Json;
using App.LogicInterfaces;
using DTOs.DTOs.CategoryRequest;
using DTOs.Models;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic;

public class CategoryRequestLogic : ICategoryRequestLogic
{
    private CategoryRequestService.CategoryRequestServiceClient client;
    
    public CategoryRequestLogic(GRPCService service)
    {
        GrpcChannel channel = service.Channel;
        client = new CategoryRequestService.CategoryRequestServiceClient(channel); 
    }
    
    public async Task CreateCategoryRequest(CreateCategoryRequestDTO dto)
    {
        try
        {
            await client.CreateCategoryRequestAsync(new CreateCategoryRequestRequest
            {
                Name = dto.CategoryName,
                UserId = dto.UserId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error creating category request");
        }
    }

    public async Task DeleteCategoryRequest(int id)
    {
        try
        {
            await client.DeleteCategoryRequestAsync(new DeleteCategoryRequestRequest
            {
                Id = id
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error deleting category request");
        }
    }

    public async  Task<CategoryRequest> GetCategoryRequestById(int id)
    {
        try
        {
            var response = await client.GetCategoryRequestAsync(
                new GetCategoryRequestRequest()
                {
                    Id = id
                });
            return new CategoryRequest(response.Date, response.CategoryName, response.UserId, response.Id);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<CategoryRequest>> GetAllCategoryRequests()
    {
        try
        {
            GetAllCategoriesRequestResponse response = await client.GetAllCategoriesRequestAsync(new EmptyGetAllCategoriesRequestRequest());
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true,
                WriteIndented = true
            };
            List<CategoryRequest> categoryRequests = JsonSerializer.Deserialize<List<CategoryRequest>>(json, options);
            return categoryRequests;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting all category requests");
        }
    }

    public async Task<List<CategoryRequest>> GetCategoryRequestsByName(string name)
    {
        try
        {
            GetAllCategoriesRequestResponse response = await client.GetCateogryRequestByNameAsync(new GetCategoryRequestByNameRequest
            {
                CategoryName = name
            });
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true,
                WriteIndented = true
            };
            List<CategoryRequest> categoryRequests = JsonSerializer.Deserialize<List<CategoryRequest>>(json, options);
            return categoryRequests;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting category requests by name");
        }
    }

    public async Task ApproveCategoryRequest(string categoryName, int addedBy)
    {
        try
        {
            await client.ApproveCategoryRequestAsync(new ApproveCategoryRequestRequest
            {
                CategoryName = categoryName,
                AddedBy = addedBy
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error approving category request");
        }
    }

    public async  Task DisapproveCategoryRequest(string categoryName)
    {
        try
        {
            await client.DisapproveCategoryRequestAsync(new DisapproveCategoryRequestRequest
            {
                    CategoryName = categoryName
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error disapproving categoryr request");
        }
    }
}