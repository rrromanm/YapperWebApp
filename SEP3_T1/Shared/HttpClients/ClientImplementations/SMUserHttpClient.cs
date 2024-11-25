using System.Net.Http.Json;
using DTOs.Models;
using DTOs.User;
using HttpClients.ClientInterfaces;

namespace HttpClients.ClientImplementations;

public class SMUserHttpClient : ISMUserService
{
    private readonly HttpClient _client;
    
    public SMUserHttpClient(HttpClient client)
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
        // Create a PATCH request
        var request = new HttpRequestMessage(HttpMethod.Patch, "https://localhost:7211/SMUser")
        {
            Content = JsonContent.Create(dto) // Use JsonContent to serialize the DTO into JSON
        };

        // Send the PATCH request
        HttpResponseMessage response = await _client.SendAsync(request);

        // Check if the response indicates success
        if (!response.IsSuccessStatusCode)
        {
            string responseContent = await response.Content.ReadAsStringAsync();
            Console.WriteLine($"API Error: {responseContent}");
            throw new Exception($"API call failed with status code {response.StatusCode}. Details: {responseContent}");
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
            if (response.StatusCode == System.Net.HttpStatusCode.NotFound)
            {
                return null; // Return null if the user is not found
            }
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<User>();
    }

    public async Task<User> GetByUserId(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"https://localhost:7211/SMUser/{userId}");
        if (!response.IsSuccessStatusCode)
        {
            if (response.StatusCode == System.Net.HttpStatusCode.NotFound)
            {
                return null; // Return null if the user is not found
            }
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<User>();
    }
}