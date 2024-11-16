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
}