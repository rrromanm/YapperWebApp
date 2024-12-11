using System.Net.Http.Json;
using DTOs.Models;
using DTOs.User;
using HttpClients.ClientInterfaces;
using System.Text.Json;

namespace HttpClients.ClientImplementations;

public class CategoryHttpClient : ICategoryService
{
    private readonly HttpClient _client;

    public CategoryHttpClient(HttpClient client)
    {
        _client = client;
    }

    public async Task CreateCategory(CreateCategoryDTO dto)
    {
        HttpResponseMessage response = await _client.PostAsJsonAsync("/Categories", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task UpdateCategory(UpdateCategoryDTO dto)
    {
        HttpResponseMessage response = await _client.PatchAsJsonAsync($"/Categories", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task DeleteCategory(int id)
    {
        HttpResponseMessage response = await _client.DeleteAsync($"/Categories/{id}");
        if(!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task<Category> GetCategoryById(int id)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Categories/{id}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        string content = await response.Content.ReadAsStringAsync();
        return JsonSerializer.Deserialize<Category>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
    }

    public async Task<List<Category>> GetAllCategories()
    {
        HttpResponseMessage response = await _client.GetAsync("/Categories");
        string content = await response.Content.ReadAsStringAsync();
        if (!response.IsSuccessStatusCode)
        {
            throw new Exception(content);
        }

        return JsonSerializer.Deserialize<List<Category>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
    }

    public async Task<Category> GetCategoryByName(string name)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Categories/Name/{name}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        string content = await response.Content.ReadAsStringAsync();
        return JsonSerializer.Deserialize<Category>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
    }
}