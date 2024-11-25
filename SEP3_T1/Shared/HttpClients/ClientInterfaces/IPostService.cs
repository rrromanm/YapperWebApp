using DTOs.Models;
using DTOs.User.PostDTOs;

namespace HttpClients.ClientInterfaces;

public interface IPostService
{
    Task CreatePost(CreatePostDTO dto);
    Task UpdatePost(UpdatePostDTO dto);
    Task DeletePost(int id);
    Task<Post> GetPost(int id);
    Task<List<Post>> GetAllPosts();
    Task<List<Post>> GetFollowingPosts(int userId);
    Task<List<Post>> GetPostsByUserId(int userId);
    Task<List<Post>> GetPostsByCategoryId(int categoryId);
    Task<List<Post>> GetLikedPosts(int userId);
    Task LikePost(int userId, int postId);
    Task UnlikePost(int userId, int postId);
}