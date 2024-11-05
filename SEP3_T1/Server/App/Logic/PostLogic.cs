using App.LogicInterfaces;
using DTOs.User.PostDTOs;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic;

public class PostLogic : IPostLogic
{
    private PostService.PostServiceClient _client;
    
    public Task CreatePost(CreatePostDTO dto)
    {
        throw new NotImplementedException();
    }
}