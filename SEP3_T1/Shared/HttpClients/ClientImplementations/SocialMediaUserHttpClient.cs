using System.Net.Http.Json;
using DTOs.User;
using HttpClients.ClientInterfaces;

namespace HttpClients.ClientImplementations;

public class SocialMediaUserHttpClient : IUserServices
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
        HttpResponseMessage response = await _client.PutAsJsonAsync($"/Users/{dto.AccountId}", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task DeleteSMUser(int accountId)
    {
        HttpResponseMessage response = await _client.DeleteAsync($"/Users/{accountId}");
        if(!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }
}