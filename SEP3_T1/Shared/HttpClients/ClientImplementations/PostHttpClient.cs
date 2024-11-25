using System.Net.Http.Json;
using DTOs.Models;
using DTOs.User.PostDTOs;
using HttpClients.ClientInterfaces;

namespace HttpClients.ClientImplementations;

public class PostHttpClient : IPostService
{
    private readonly HttpClient _client;
    
    public PostHttpClient(HttpClient client)
    {
        _client = client;
    }
    
    public async Task CreatePost(CreatePostDTO dto)
    {
        HttpResponseMessage response = await _client.PostAsJsonAsync("/Posts", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task UpdatePost(UpdatePostDTO dto)
    {
        HttpResponseMessage response = await _client.PutAsJsonAsync($"/Posts/{dto.PostId}", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task DeletePost(int id)
    {
        HttpResponseMessage response = await _client.DeleteAsync($"/Posts/{id}");
        if(!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task<List<Post>> GetPostsByUserId(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"https://localhost:7211/Post/GetPostsByUserId/{userId}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }

    public async Task<Post> GetPost(int id)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Posts/{id}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<Post>();
    }
    
    public async Task<List<Post>> GetAllPosts()
    {
        HttpResponseMessage response = await _client.GetAsync("/Posts");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }
    
    public async Task<List<Post>> GetPostsByCategory(int categoryId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Posts/Category/{categoryId}");
        
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        
        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }
    
    public async Task<List<Post>> GetPostsByUser(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Posts/User/{userId}");
        
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        
        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }
}

