using DTOs.User.PostDTOs;

namespace App.LogicInterfaces;

public interface IPostLogic
{
    Task CreatePost(CreatePostDTO dto);
}