
using HttpClients.ClientInterfaces;
using System.Net.Http.Json;
using DTOs.DTOs;
using DTOs.Models;
using System.Text.Json;
using DTOs.DTOs.Comment;

namespace HttpClients.ClientImplementations;

public class CommentHttpClient : ICommentService
{
   private readonly HttpClient _client;
   public CommentHttpClient(HttpClient client)
   {
       _client = client;
   }
   public async Task CreateCommentAsync(CreateCommentDTO dto)
   {
       HttpResponseMessage response = await _client.PostAsJsonAsync("/Comment", dto);
       if (!response.IsSuccessStatusCode)
       {
           string e = await response.Content.ReadAsStringAsync();
           throw new Exception(e);
       }
   }

   public async Task UpdateCommentAsync(UpdateCommentDTO dto)
   {
         HttpResponseMessage response = await _client.PutAsJsonAsync($"/Comment/{dto.commentId}", dto);
         if (!response.IsSuccessStatusCode)
         {
              string e = await response.Content.ReadAsStringAsync();
              throw new Exception(e);
         }
   }

   public async Task DeleteCommentAsync(int id)
   {
       HttpResponseMessage response = await _client.DeleteAsync($"/Comment/{id}");
       if (!response.IsSuccessStatusCode)
       {
           string e = await response.Content.ReadAsStringAsync();
           throw new Exception(e);
       }
   }

   public async Task<Comment> GetCommentAsync(int id)
   {
       HttpResponseMessage response = await _client.GetAsync($"/Comment/{id}");
       if (!response.IsSuccessStatusCode)
       {
           string e = await response.Content.ReadAsStringAsync();
           throw new Exception(e);
       }
       string content = await response.Content.ReadAsStringAsync();
       return JsonSerializer.Deserialize<Comment>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
   }

   public async Task<List<Comment>> GetAllCommentsAsync()
   {
       HttpResponseMessage response = await _client.GetAsync("/Comment");
       if (!response.IsSuccessStatusCode)
       {
           string e = await response.Content.ReadAsStringAsync();
           throw new Exception(e);
       }
       string content = await response.Content.ReadAsStringAsync();
       return JsonSerializer.Deserialize<List<Comment>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true }); 
   }

   public async Task<List<Comment>> GetCommentsByPostIdAsync(int postId)
   {
       try
       {
           HttpResponseMessage response = await _client.GetAsync($"/Comment/post/{postId}");
           if (!response.IsSuccessStatusCode)
           {
               string errorContent = await response.Content.ReadAsStringAsync();
               throw new Exception($"Failed to load comments: {response.ReasonPhrase}");
           }
           string content = await response.Content.ReadAsStringAsync();
           return JsonSerializer.Deserialize<List<Comment>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
       }
       catch (Exception ex)
       {
           Console.WriteLine($"Error in GetCommentsByPostIdAsync: {ex.Message}");
           throw;
       }
   }

   public async Task<List<Comment>> GetCommentsByUserIdAsync(int userId)
   {
         HttpResponseMessage response = await _client.GetAsync($"/Comment/user/{userId}");
         if (!response.IsSuccessStatusCode)
         {
              string e = await response.Content.ReadAsStringAsync();
              throw new Exception(e);
         }
         string content = await response.Content.ReadAsStringAsync();
         return JsonSerializer.Deserialize<List<Comment>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
   }

   public async Task LikeCommentAsync(int commentId, int userId)
   {
         HttpResponseMessage response = await _client.PostAsync($"/Comment/like/{commentId}/{userId}", null);
         if (!response.IsSuccessStatusCode)
         {
             string e = await response.Content.ReadAsStringAsync();
             throw new Exception(e);
         }
   }
   public async Task UnlikeCommentAsync(int commentId, int userId)
   {
         HttpResponseMessage response = await _client.PostAsync($"/Comment/unlike/{commentId}/{userId}", null);
         if (!response.IsSuccessStatusCode)
         {
             string e = await response.Content.ReadAsStringAsync();
             throw new Exception(e);
         }
   }
   public async Task<List<Comment>> GetAllLikedCommentsAsync(int userId)
   {
         HttpResponseMessage response = await _client.GetAsync($"/Comment/liked/{userId}");
         if (!response.IsSuccessStatusCode)
         {
             string e = await response.Content.ReadAsStringAsync();
             throw new Exception(e);
         }
         string content = await response.Content.ReadAsStringAsync();
         return JsonSerializer.Deserialize<List<Comment>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
   }
}