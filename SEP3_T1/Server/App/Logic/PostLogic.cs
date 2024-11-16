using App.LogicInterfaces;
using DTOs.Models;
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

    public async Task UpdatePost(UpdatePostDTO dto)
    {
        try
        {
            await client.UpdatePostAsync(new UpdatePostRequest
            {
                PostId = dto.PostId,
                Title = dto.Title,
                Content = dto.Content,
                CategoryId = dto.CategoryId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task DeletePost(int postId)
    {
        try
        {
            await client.DeletePostAsync(new DeletePostRequest
            {
                PostId = postId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task GetPost(int postId)
    {
        try
        {
            client.GetPost(new GetPostRequest()
            {
                PostId = postId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<Post>> GetPosts()
    {
        try
        {
            List<Post> posts = new List<Post>();
            return posts;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<Post>> GetFollowingPosts(int userId)
    {
        try
        {
            List<Post> posts = new List<Post>();
            return posts;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}