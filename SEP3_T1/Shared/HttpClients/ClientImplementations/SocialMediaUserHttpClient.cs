using System.Net.Http.Json;
using DTOs.Models;
using DTOs.User;
using HttpClients.ClientInterfaces;

namespace HttpClients.ClientImplementations;

public class SocialMediaUserHttpClient : IUserService
{
    private readonly HttpClient _client;
    
    public SocialMediaUserHttpClient(HttpClient client)
    {
        _client = client;
    }
    
    public async Task CreateSMUser(CreateUserDTO dto)
    {
        HttpResponseMessage response = await _client.PostAsJsonAsync("/Users", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task UpdateSMUser(UpdateUserDTO dto)
    {
        HttpResponseMessage response = await _client.PutAsJsonAsync($"/Users/{dto.UserId}", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task DeleteSMUser(int userId)
    {
        HttpResponseMessage response = await _client.DeleteAsync($"/Users/{userId}");
        if(!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task<User> GetByUsername(string username)
    {
        HttpResponseMessage response = await _client.GetAsync($"https://localhost:7211/SMUser/{username}");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<User>();
    }
}