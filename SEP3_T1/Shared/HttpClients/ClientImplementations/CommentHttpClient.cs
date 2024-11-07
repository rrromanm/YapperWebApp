
using HttpClients.ClientInterfaces;
using System.Net.Http.Json;
using DTOs.DTOs;

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
}