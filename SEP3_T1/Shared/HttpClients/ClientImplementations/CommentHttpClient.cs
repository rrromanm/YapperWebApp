
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
       HttpResponseMessage response = await _client.PostAsJsonAsync("/Comments", dto);
       if (!response.IsSuccessStatusCode)
       {
           string e = await response.Content.ReadAsStringAsync();
           throw new Exception(e);
       }
   }

   public async Task UpdateCommentAsync(UpdateCommentDTO dto)
   {
         HttpResponseMessage response = await _client.PutAsJsonAsync($"/Comments/{dto.commentId}", dto);
         if (!response.IsSuccessStatusCode)
         {
              string e = await response.Content.ReadAsStringAsync();
              throw new Exception(e);
         }
   }

   public async Task DeleteCommentAsync(int id)
   {
       HttpResponseMessage response = await _client.DeleteAsync($"/Comments/{id}");
       if (!response.IsSuccessStatusCode)
       {
           string e = await response.Content.ReadAsStringAsync();
           throw new Exception(e);
       }
   }

   public async Task<Comment> GetCommentAsync(int id)
   {
       HttpResponseMessage response = await _client.GetAsync($"/Comments/{id}");
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
       HttpResponseMessage response = await _client.GetAsync("/Comments");
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
       HttpResponseMessage response = await _client.GetAsync($"/Comments/Post/{postId}");
       if (!response.IsSuccessStatusCode)
       {
              string e = await response.Content.ReadAsStringAsync();
              throw new Exception(e);
       }
       string content = await response.Content.ReadAsStringAsync();
         return JsonSerializer.Deserialize<List<Comment>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
            
   }

   public async Task<List<Comment>> GetCommentsByUserIdAsync(int userId)
   {
         HttpResponseMessage response = await _client.GetAsync($"/Comments/User/{userId}");
         if (!response.IsSuccessStatusCode)
         {
              string e = await response.Content.ReadAsStringAsync();
              throw new Exception(e);
         }
         string content = await response.Content.ReadAsStringAsync();
         return JsonSerializer.Deserialize<List<Comment>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
   }
}