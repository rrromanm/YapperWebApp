using System.Text.Json;
using App.LogicInterfaces;
using DTOs.Models;
using DTOs.User;
using Grpc.Core;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic
{
    public class CategoryLogic : ICategoryLogic
    {
        private CategoryService.CategoryServiceClient _client;

        public CategoryLogic(GRPCService service)
        {
            GrpcChannel channel = service.Channel;
            _client = new CategoryService.CategoryServiceClient(channel);
        }

        public async Task CreateCategoryAsync(CreateCategoryDTO dto)
        {
            try
            {
                await _client.CreateCategoryAsync(new CreateCategoryRequest
                {
                    Name = dto.Name,
                    AddedBy = dto.addedBy
                });
            }
            catch (RpcException rpcEx)
            {
                Console.WriteLine($"gRPC Error: {rpcEx.Status.StatusCode}, Detail: {rpcEx.Status.Detail}");
                throw new Exception("Error creating category", rpcEx);
            }
        }

        public async Task UpdateCategoryAsync(UpdateCategoryDTO dto)
        {
            try
            {
                await _client.UpdateCategoryAsync(new UpdateCategoryRequest
                {   Id = dto.id,
                    Name = dto.name
                });
            }
            catch (RpcException rpcEx)
            {
                Console.WriteLine($"gRPC Error: {rpcEx.Status.StatusCode}, Detail: {rpcEx.Status.Detail}");
                throw new Exception("Error updating category", rpcEx);
            }
        }

        public async Task DeleteCategoryAsync(int id)
        {
            try
            {
                await _client.DeleteCategoryAsync(new DeleteCategoryRequest
                {
                    Id = id
                });
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw new Exception("Error deleting category, category not found or used by a post");
            }
        }

        public async Task<Category> GetCategoryById(int id)
        {
            try
            {
                CategoryResponse response = await _client.GetCategoryAsync(new GetCategoryRequest
                {
                    Id = id
                });
                Category category = new Category(response.Id, response.Name, response.AddedBy);
                return category;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw new Exception("Error getting category");
            }
        }

        public async Task<List<Category>> GetAllCategoriesAsync()
        {
            try
            {
                GetAllCategoriesResponse response = await _client.GetAllCategoriesAsync(new EmptyGetAllCategoriesRequest());
                string json = response.List;
                JsonSerializerOptions options = new JsonSerializerOptions
                {
                    WriteIndented = true,
                    PropertyNameCaseInsensitive = true
                };
                List<Category> categories = JsonSerializer.Deserialize<List<Category>>(json, options);
                return categories;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw new Exception("Error getting all categories");
            }
        }

        public async Task<Category> GetCategoryByName(string name)
        {
            try
            {
                CategoryResponse response = await _client.GetCategoryByNameAsync(new GetCategoryByNameRequest
                {
                    Name = name
                });
                Category category = new Category(response.Id, response.Name, response.AddedBy);
                return category;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw new Exception("Error getting category by name");
            }
        }
    }
}