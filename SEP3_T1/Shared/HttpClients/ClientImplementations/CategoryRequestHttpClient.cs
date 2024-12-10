using System.Net.Http.Json;
using System.Text.Json;
using DTOs.DTOs.CategoryRequest;
using HttpClients.ClientInterfaces;

namespace HttpClients.ClientImplementations;

public class CategoryRequestHttpClient : ICategoryRequestService
{
    private readonly HttpClient _client;
    public CategoryRequestHttpClient(HttpClient client)
    {
        _client = client;
    }
    
    public async Task CreateCategoryRequest(CreateCategoryRequestDTO dto)
    {
        HttpResponseMessage response = await _client.PostAsJsonAsync("/CategoryRequest", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task DeleteCategoryRequest(int id)
    {
        HttpResponseMessage response = await _client.DeleteAsync($"/CategoryRequest/{id}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task<CategoryRequestDTO> GetCategoryRequestById(int id)
    {
      HttpResponseMessage response = await _client.GetAsync($"/CategoryRequest/{id}");  
      if(!response.IsSuccessStatusCode)
      {
          string e = await response.Content.ReadAsStringAsync();
          throw new Exception(e);
      }
      string content = await response.Content.ReadAsStringAsync();
      return JsonSerializer.Deserialize<CategoryRequestDTO>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
    }

    public async Task<List<CategoryRequestDTO>> GetAllCategoryRequests()
    {
        HttpResponseMessage response = await _client.GetAsync("/CategoryRequest");
        string content = await response.Content.ReadAsStringAsync();
        if(!response.IsSuccessStatusCode)
        {
            throw new Exception(content);
        }
        return JsonSerializer.Deserialize<List<CategoryRequestDTO>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
    }

    public async Task<List<CategoryRequestDTO>> GetCategoryRequestsByName(string name)
    {
        HttpResponseMessage response = await _client.GetAsync($"/CategoryRequest/{name}");
        if(!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        string content = await response.Content.ReadAsStringAsync();
        return JsonSerializer.Deserialize<List<CategoryRequestDTO>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
    }

    public async Task ApproveCategoryRequest(string categoryName, int addedBy)
    {
        HttpResponseMessage response = await _client.PostAsync($"/CategoryRequest/{categoryName}/Approve/{addedBy}", null);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task DisapproveCategoryRequest(string categoryName)
    {
        HttpResponseMessage response = await _client.PostAsync($"/CategoryRequest/{categoryName}/Disapprove", null);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }
}