using System.Text.Json;
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
                Body = dto.Body,
                UserId= dto.UserId,
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
                Title = dto.Title,
                Body = dto.Body,
                PostId = dto.PostId,
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

    public async Task<Post> GetPost(int postId)
    {
        try
        {
            var response = await client.GetPostAsync(new GetPostRequest()
            {
                PostId = postId
            });
            
            Post post = new Post(response.Title, response.Body,response.LikeCount, response.CommentCount, response.Date, response.CategoryId, response.PostId, response.UserId);
            
            Console.WriteLine(response.Date);

            return post;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<Post>> GetAllPosts()
    {
        try
        {
            GetAllPostsResponse response = await client.GetAllPostsAsync(new EmptyGetAllPostsRequest());
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<Post> posts = JsonSerializer.Deserialize<List<Post>>(json, options);
            Console.WriteLine(posts);
            return posts;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting all posts");
        }
    }

    public async Task<List<Post>> GetFollowingPosts(int userId)
    {
        try
        {
            GetAllFollowingPostsResponse response = await client.GetAllFollowingPostsAsync(new GetAllFollowingPostsRequest()
            {
                UserId = userId
            });
            string json = response.List;
            
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<Post> posts = JsonSerializer.Deserialize<List<Post>>(json, options);
            
            return posts;
            
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<Post>> GetPostsByUserId(int userId)
    {
        try
        {
            GetAllPostsByIdResponse response = await client.GetAllPostsByIdAsync(new GetAllPostsByIdRequest
            {
                UserId = userId
            });
            string json = response.List;
            
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<Post> posts = JsonSerializer.Deserialize<List<Post>>(json, options);
            
            return posts;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<Post>> GetPostsByCategoryId(int categoryId)
    {
        try
        {
            GetAllPostsByCategoryResponse response = await client.GetAllPostsByCategoryAsync(new GetAllPostsByCategoryRequest
            {
                CategoryId = categoryId
            });
            string json = response.List;
            
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<Post> posts = JsonSerializer.Deserialize<List<Post>>(json, options);
            
            return posts;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<Post>> GetLikedPosts(int userId)
    {
        try
        {
            GetAllLikedPostsResponse response = await client.GetAllLikedPostsAsync(new GetAllLikedPostsRequest
            {
                UserId = userId
            });
            string json = response.List;
            
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<Post> posts = JsonSerializer.Deserialize<List<Post>>(json, options);
            
            return posts;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task LikePost(int userId, int postId)
    {
        try
        {
            await client.LikePostAsync(new LikePostRequest
            {
                UserId = userId,
                PostId = postId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }   

    public async Task UnlikePost(int userId, int postId)
    {
        try
        {
            await client.UnlikePostAsync(new UnlikePostRequest
            {
                UserId = userId,
                PostId = postId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}