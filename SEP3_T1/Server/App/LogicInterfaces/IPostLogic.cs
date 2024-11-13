using DTOs.Models;
using DTOs.User.PostDTOs;

namespace App.LogicInterfaces;

public interface IPostLogic
{
    Task CreatePost(CreatePostDTO dto);
    Task UpdatePost(UpdatePostDTO dto);
    Task DeletePost(int postId);
    Task GetPost(int postId);
    Task<List<Post>> GetPosts();
    Task<List<Post>> GetFollowingPosts(int userId);
}