using App.LogicInterfaces;
using DTOs.User.PostDTOs;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic;

public class PostLogic : IPostLogic
{
    private PostService.PostServiceClient client;
    
    public PostLogic(GRPCService service)
    {
        GrpcChannel channel = service.Channel;
        client = new PostService.PostServiceClient(channel);
    }
    
    public async Task CreatePost(CreatePostDTO dto)
    {
        try
        {
            await client.CreatePostAsync(new CreatePostRequest
            {
                Title = dto.Title,
                Content = dto.Content,
                AccountId = dto.AccountId,
                CategoryId = dto.CategoryId
            });
        }
        catch (Grpc.Core.RpcException e)
        {
            Console.WriteLine($"gRPC Error: {e.Status.StatusCode}, Detail: {e.Status.Detail}");
            throw new Exception("Error creating post", e);
        }
    }

    public Task UpdatePost(UpdatePostDTO dto)
    {
        throw new NotImplementedException();
    }

    public Task DeletePost(int postId)
    {
        throw new NotImplementedException();
    }
}