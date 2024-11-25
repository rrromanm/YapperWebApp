
using DTOs.Models;
using DTOs.User.PostDTOs;

namespace HttpClients.ClientInterfaces;

public interface IPostService
{
    Task CreatePost(CreatePostDTO dto);
    Task UpdatePost(UpdatePostDTO dto);
    Task DeletePost(int id);
    Task<List<Post>> GetPostsByUserId(int userId);
}