using System.Security.Claims;
using System.Text.Json;
using DTOs.DTOs.Auth;
using DTOs.Models;
using DTOs.User;
using DTOs.Util;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;

namespace BlazorApp.Auth;

public class SimpleAuthProvider : AuthenticationStateProvider
{
    private readonly HttpClient client;
    private readonly IJSRuntime jsRuntime;

    public SimpleAuthProvider(HttpClient client, IJSRuntime jsRuntime)
    {
        this.client = client;
        this.jsRuntime = jsRuntime;
    }

    public async Task Login(string username, string password)
    {
        try
        {
            PasswordManager passwordManager = new PasswordManager();
            HttpResponseMessage response = await client.PostAsJsonAsync(
                $"/login",
                new LoginRequestDTO(username, passwordManager.HashPassword(password)));

            string content = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                Console.WriteLine($"Login failed: {content}");
                throw new Exception(content);
            }
            UserDTO userDto = JsonSerializer.Deserialize<UserDTO>(content, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            })!;

            string serialisedData = JsonSerializer.Serialize(userDto);
            await jsRuntime.InvokeVoidAsync("sessionStorage.setItem", "currentUser", serialisedData);

            List<Claim> claims = new List<Claim>()
            {
                new Claim(ClaimTypes.Name, userDto.Username),
                new Claim(ClaimTypes.Email, userDto.Email),
                new Claim("Id", userDto.Id.ToString()),
                new Claim(ClaimTypes.Role, "User")
            };

            ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth");
            ClaimsPrincipal claimsPrincipal = new ClaimsPrincipal(identity);

            NotifyAuthenticationStateChanged(
                Task.FromResult(new AuthenticationState(claimsPrincipal))
            );
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Exception in Login method: {ex.Message}");
            throw;
        }
    }

    public async Task ModeratorLogin(string username, string password)
    {
        PasswordManager passwordManager = new PasswordManager();
        HttpResponseMessage response = await client.PostAsJsonAsync(
            $"/moderatorLogin",
            new LoginRequestDTO(username, passwordManager.HashPassword(password)));

        string content = await response.Content.ReadAsStringAsync();
        if (!response.IsSuccessStatusCode)
        {
            throw new Exception(content);
        }
        Moderator moderator = JsonSerializer.Deserialize<Moderator>(content, new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        })!;

        string serialisedData = JsonSerializer.Serialize(moderator);
        await jsRuntime.InvokeVoidAsync("sessionStorage.setItem", "currentUser", serialisedData);

        List<Claim> claims = new List<Claim>()
        {
            new Claim(ClaimTypes.Name, moderator.Username),
            new Claim("Id", moderator.Id.ToString()),
            new Claim(ClaimTypes.Role, "Moderator"),
        };

        ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth");
        ClaimsPrincipal claimsPrincipal = new ClaimsPrincipal(identity);

        NotifyAuthenticationStateChanged(
            Task.FromResult(new AuthenticationState(claimsPrincipal))
        );
    }

    public async Task Register(CreateUserDTO dto)
    {
        HttpResponseMessage response = await client.PostAsJsonAsync(
            $"/register",
            dto);

        string content = await response.Content.ReadAsStringAsync();
        if (!response.IsSuccessStatusCode)
        {
            throw new Exception(content);
        }

        UserDTO userDto = JsonSerializer.Deserialize<UserDTO>(content, new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        })!;

        string serialisedData = JsonSerializer.Serialize(userDto);
        await jsRuntime.InvokeVoidAsync("sessionStorage.setItem", "currentUser", serialisedData);

        List<Claim> claims = new List<Claim>()
        {
            new Claim(ClaimTypes.Name, userDto.Username),
            new Claim(ClaimTypes.Email, userDto.Email),
            new Claim("Id", userDto.Id.ToString()),
            new Claim(ClaimTypes.Role, "User") 
        };

        ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth");
        ClaimsPrincipal claimsPrincipal = new ClaimsPrincipal(identity);

        NotifyAuthenticationStateChanged(
            Task.FromResult(new AuthenticationState(claimsPrincipal))
        );
    }

    public async Task Logout()
    {
        await jsRuntime.InvokeVoidAsync("sessionStorage.removeItem", "currentUser");

        ClaimsPrincipal claimsPrincipal = new ClaimsPrincipal(new ClaimsIdentity());
        NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(claimsPrincipal)));
    }

    public override async Task<AuthenticationState> GetAuthenticationStateAsync()
    {
        string userAsJson = "";
        try
        {
            userAsJson = await jsRuntime.InvokeAsync<string>("sessionStorage.getItem", "currentUser");
        }
        catch (InvalidOperationException e)
        {
            return new AuthenticationState(new ClaimsPrincipal(new ClaimsIdentity()));
        }

        if (string.IsNullOrEmpty(userAsJson))
        {
            return new AuthenticationState(new ClaimsPrincipal(new ClaimsIdentity()));
        }

        dynamic deserializedUser;
        if (userAsJson.Contains("\"Role\":\"Moderator\""))
        {
            deserializedUser = JsonSerializer.Deserialize<Moderator>(userAsJson)!;

            List<Claim> claims = new List<Claim>
            {
                new Claim(ClaimTypes.Name, deserializedUser.Username),
                new Claim(ClaimTypes.Role, deserializedUser.Role),
                new Claim("Id", deserializedUser.Id.ToString())
            };

            ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth");
            ClaimsPrincipal claimsPrincipal = new ClaimsPrincipal(identity);
            return new AuthenticationState(claimsPrincipal);
        }
        else
        {
            deserializedUser = JsonSerializer.Deserialize<UserDTO>(userAsJson)!;

            List<Claim> claims = new List<Claim>
            {
                new Claim(ClaimTypes.Name, deserializedUser.Username),
                new Claim(ClaimTypes.Email, deserializedUser.Email),
                new Claim("Id", deserializedUser.Id.ToString()),
                new Claim(ClaimTypes.Role, "User")
            };

            ClaimsIdentity identity = new ClaimsIdentity(claims, "apiauth");
            ClaimsPrincipal claimsPrincipal = new ClaimsPrincipal(identity);
            return new AuthenticationState(claimsPrincipal);
        }
    }
}