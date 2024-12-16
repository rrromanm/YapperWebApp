using System.Net.Http.Json;
using DTOs.Models;
using DTOs.User;
using HttpClients.ClientInterfaces;
using Newtonsoft.Json;

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
        HttpResponseMessage response = await _client.PatchAsJsonAsync("/SMUser", dto);
        if (!response.IsSuccessStatusCode)
        {
            string responseContent = await response.Content.ReadAsStringAsync();
            throw new Exception(responseContent);
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

    public async Task<SMUser> GetByUsername(string username)
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser/{username}");
        if (!response.IsSuccessStatusCode)
        {
            if (response.StatusCode == System.Net.HttpStatusCode.NotFound)
            {
                throw new HttpRequestException("SMUser does not exist.", null, System.Net.HttpStatusCode.NotFound);
            }
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<SMUser>();
    }

    public async Task<SMUser> GetByUserId(int userId)
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser/{userId}");
        if (!response.IsSuccessStatusCode)
        {
            if (response.StatusCode == System.Net.HttpStatusCode.NotFound)
            {
                return null; 
            }
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<SMUser>();
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

    public async Task<List<SMUser>> GetAllUsers()
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<List<SMUser>>();
    }

    public async Task<bool> IsFollowing(int followerId, int followedId)
    {
        var response = await _client.GetAsync($"/SMUser/{followerId}/isfollowing/{followedId}");
        response.EnsureSuccessStatusCode();

        var isFollowing = await response.Content.ReadFromJsonAsync<bool>();
        return isFollowing;
    }
    
    public async Task<List<FollowersDTO>> GetThreeRandomUsers(int id)
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser/{id}/Random");
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<List<FollowersDTO>>();
    }

    public async Task<List<FollowersDTO>> GetUsersBySearch(string searchText)
    {
        HttpResponseMessage response = await _client.GetAsync($"/SMUser/search/{searchText}");
        if (!response.IsSuccessStatusCode)
        {
            string e = response.Content.ReadAsStringAsync().Result;
            throw new Exception(e);
        }
        return await response.Content.ReadFromJsonAsync<List<FollowersDTO>>();
    }
    
    
}