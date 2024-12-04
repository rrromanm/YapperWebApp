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
        HttpResponseMessage response = await _client.PostAsJsonAsync("/Post", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task UpdatePost(UpdatePostDTO dto)
    {
        HttpResponseMessage response = await _client.PatchAsJsonAsync($"/Post/", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task DeletePost(int id)
    {
        HttpResponseMessage response = await _client.DeleteAsync($"/Post/{id}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task<Post> GetPost(int id)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Post/{id}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }

        return await response.Content.ReadFromJsonAsync<Post>();
    }

    public async Task<List<Post>> GetAllPosts()
    {
        HttpResponseMessage response = await _client.GetAsync("/Post");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }

        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }

    public async Task<List<Post>> GetFollowingPosts(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Post/GetFollowingPosts/{userId}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }

        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }

    public async Task<List<Post>> GetPostsByUserId(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Post/GetPostsByUserId/{userId}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }

        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }

    public async Task<List<Post>> GetPostsByCategoryId(int categoryId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Post/GetPostsByCategoryId/{categoryId}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }

        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }

    public async Task<List<Post>> GetLikedPosts(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/Post/GetLikedPosts/{userId}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }

        return await response.Content.ReadFromJsonAsync<List<Post>>();
    }

    public async Task LikePost(int userId, int postId)
    {
        HttpResponseMessage response = await _client.PostAsync($"/Post/LikePost/{userId}/{postId}", null);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task UnlikePost(int userId, int postId)
    {
        HttpResponseMessage response = await _client.PostAsync($"/Post/UnlikePost/{userId}/{postId}", null);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

}
    