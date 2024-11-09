using DTOs.User.PostDTOs;

namespace App.LogicInterfaces;

public interface IPostLogic
{
    Task CreatePost(CreatePostDTO dto);
    Task UpdatePost(UpdatePostDTO dto);
    Task DeletePost(int postId);
    Task GetPost(int postId);
}