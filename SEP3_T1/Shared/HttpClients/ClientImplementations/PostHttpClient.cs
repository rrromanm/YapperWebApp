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

    public Task<List<Post>> GetFollowingPosts(int userId)
    {
        HttpResponseMessage responseMessage = _client.GetAsync($"/Posts/GetFollowingPosts/{userId}").Result;
        if (!responseMessage.IsSuccessStatusCode)
        {
            string e = responseMessage.Content.ReadAsStringAsync().Result;
            throw new Exception(e);
        }
        return responseMessage.Content.ReadFromJsonAsync<List<Post>>();
    }

    public Task<List<Post>> GetPostsByUserId(int userId)
    {
        HttpResponseMessage responseMessage = _client.GetAsync($"/Posts/User/{userId}").Result;
        if (!responseMessage.IsSuccessStatusCode)
        {
            string e = responseMessage.Content.ReadAsStringAsync().Result;
            throw new Exception(e);
        }
        return responseMessage.Content.ReadFromJsonAsync<List<Post>>();
    }

    public Task<List<Post>> GetPostsByCategoryId(int categoryId)
    {
        HttpResponseMessage responseMessage = _client.GetAsync($"/Posts/Category/{categoryId}").Result;
        if (!responseMessage.IsSuccessStatusCode)
        {
            string e = responseMessage.Content.ReadAsStringAsync().Result;
            throw new Exception(e);
        }
        return responseMessage.Content.ReadFromJsonAsync<List<Post>>();
    }

    public Task<List<Post>> GetLikedPosts(int userId)
    {
        HttpResponseMessage responseMessage = _client.GetAsync($"/Posts/Liked/{userId}").Result;
        if (!responseMessage.IsSuccessStatusCode)
        {
            string e = responseMessage.Content.ReadAsStringAsync().Result;
            throw new Exception(e);
        }
        return responseMessage.Content.ReadFromJsonAsync<List<Post>>();
    }

    public Task LikePost(int userId, int postId)
    {
        HttpResponseMessage response = _client.GetAsync($"/Posts/Like/{userId}/{postId}").Result;
        if (!response.IsSuccessStatusCode)
        {
            string e = response.Content.ReadAsStringAsync().Result;
            throw new Exception(e);
        }
        return Task.CompletedTask;
    }

    public Task UnlikePost(int userId, int postId)
    {
        HttpResponseMessage response = _client.GetAsync($"/Posts/Unlike/{userId}/{postId}").Result;
        if (!response.IsSuccessStatusCode)
        {
            string e = response.Content.ReadAsStringAsync().Result;
            throw new Exception(e);
        }
        return Task.CompletedTask;
    }
}

