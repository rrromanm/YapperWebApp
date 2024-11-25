using DTOs.Models;
using DTOs.User.PostDTOs;

namespace App.LogicInterfaces;

public interface IPostLogic
{
    Task CreatePost(CreatePostDTO dto);
    Task UpdatePost(UpdatePostDTO dto);
    Task DeletePost(int postId);
    Task<Post> GetPost(int postId);
    Task<List<Post>> GetAllPosts();
    Task<List<Post>> GetFollowingPosts(int userId);
    Task<List<Post>> GetPostsByUserId(int userId);
    Task<List<Post>> GetPostsByCategoryId(int categoryId);
    Task<List<Post>> GetLikedPosts(int userId);
    Task LikePost(int userId, int postId);
    Task UnlikePost(int userId, int postId);
}