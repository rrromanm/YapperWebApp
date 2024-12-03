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
        HttpResponseMessage response = await _client.PostAsJsonAsync("/SMUser", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task UpdateSMUser(UpdateUserDTO dto)
    {
        // Create a PATCH request
        var request = new HttpRequestMessage(HttpMethod.Patch, "/SMUser")
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
        HttpResponseMessage response = await _client.DeleteAsync($"/SMUser/{userId}");
        if(!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task<User> GetByUsername(string username)
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser/{username}");
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
        HttpResponseMessage response = await _client.GetAsync($"/SMUser/{userId}");
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

    public async Task FollowUser(int followerId, int followedId)
    {
        HttpResponseMessage response = await _client.PostAsync($"/SMUser/{followerId}/Follow/{followedId}", null);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception($"Failed to follow user. Status code: {response.StatusCode}, Error: {e}");
        }
    }

    public async Task UnfollowUser(int followerId, int followedId)
    {
        HttpResponseMessage response = await _client.PostAsync($"/SMUser/{followerId}/Unfollow/{followedId}", null);
        if(!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        
    }

    public async Task<List<FollowersDTO>> GetFollowers(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser/{userId}/Followers");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<List<FollowersDTO>>();
    }

    public async Task<List<FollowersDTO>> GetFollowing(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser/{userId}/Following");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<List<FollowersDTO>>();
    }

    public async Task<List<User>> GetAllUsers()
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<List<User>>();
    }

    public async Task<bool> IsFollowing(int followerId, int followedId)
    {
        var response = await _client.GetAsync($"/SMUser/{followerId}/isfollowing/{followedId}");
        response.EnsureSuccessStatusCode();

        var isFollowing = await response.Content.ReadFromJsonAsync<bool>();
        return isFollowing;
    }
}