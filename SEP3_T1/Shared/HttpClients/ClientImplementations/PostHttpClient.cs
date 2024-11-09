using System.Net.Http.Json;
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
}