using DTOs.User.PostDTOs;

namespace HttpClients.ClientInterfaces;

public interface IPostService
{
    Task CreatePost(CreatePostDTO dto);
}