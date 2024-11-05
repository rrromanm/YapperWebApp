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
            await client.CreatePost(new CreatePostRequest
            {
                Title = dto.Title,
                Content = dto.Content,
                AccountId = dto.AccountId,
                CategoryId = dto.CategoryId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error creating post");
        }
    }
}