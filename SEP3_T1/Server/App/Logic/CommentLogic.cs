using System.Text.Json;
using App.LogicInterfaces;
using Comment;
using DTOs.DTOs;
using DTOs.DTOs.Comment;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic;

public class CommentLogic : ICommentLogic
{
    private CommentService.CommentServiceClient client;
    
    public CommentLogic(GRPCService service)
    {
        GrpcChannel channel = service.Channel;
        client = new CommentService.CommentServiceClient(channel);
    }
    
    public async Task CreateCommentAsync(CreateCommentDTO dto)
    {
        try
        {
            await client.CreateCommentAsync(new CreateCommentRequest
            {
               Body = dto.body,
               UserId = dto.userId,
               PostId = dto.postId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error creating comment");
        }
        
    }
    public async Task UpdateCommentAsync(UpdateCommentDTO dto)
    {
        try
        {
            await client.UpdateCommentAsync(new UpdateCommentRequest
            {
               CommentId = dto.commentId,
               Body = dto.body
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error updating comment");
        }
    }

    public async Task DeleteCommentAsync(int id)
    {
        try 
        {
            await client.DeleteCommentAsync(new DeleteCommentRequest
            {
                CommentId = id
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error deleting comment");
        }
    }

    public async Task<DTOs.Models.Comment> GetCommentAsync(int id)
    {
        try
        {
            var response = await client.GetCommentAsync(new GetCommentRequest
            {
                CommentId = id
            });
            return new DTOs.Models.Comment(response.Body, response.CommentDate, response.LikeCount, response.CommentId, response.UserId, response.PostId);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting comment");
        }
    }

    public async Task<List<DTOs.Models.Comment>> GetAllCommentsAsync()
    {
        try
        {
            GetAllCommentsResponse response = await client.GetAllCommentsAsync(new EmptyGetAllCommentsRequest());
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<DTOs.Models.Comment> comments = JsonSerializer.Deserialize<List<DTOs.Models.Comment>>(json, options);
            return comments;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting all comments");
        }
    }

    public async Task<List<DTOs.Models.Comment>> GetCommentsByPostIdAsync(int postId)
    {
        try
        {
            GetCommentsByPostResponse response = await client.GetCommentsByPostAsync(new GetCommentsByPostRequest
            {
                PostId = postId
            });
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<DTOs.Models.Comment> comments = JsonSerializer.Deserialize<List<DTOs.Models.Comment>>(json, options);
            return comments;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting comments by post id");
        }
    }

    public async Task<List<DTOs.Models.Comment>> GetCommentsByUserIdAsync(int userId)
    {
        try
        {
            GetCommentsByUserResponse response = await client.GetCommentsByUserAsync(new GetCommentsByUserRequest
            {
                UserId = userId
            });
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<DTOs.Models.Comment> comments = JsonSerializer.Deserialize<List<DTOs.Models.Comment>>(json, options);
            return comments;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting comments by user id");
        }
    }
    public async Task LikeCommentAsync(int commentId, int userId)
    {
        try
        {
            await client.LikeCommentAsync(new LikeCommentRequest
            {
                CommentId = commentId,
                UserId = userId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error liking comment");
        }
    }
    public async Task UnlikeCommentAsync(int commentId, int userId)
    {
        try
        {
            await client.UnlikeCommentAsync(new UnlikeCommentRequest
            {
                CommentId = commentId,
                UserId = userId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error unliking comment");
        }
    }

    public async Task<List<DTOs.Models.Comment>> GetAllLikedCommentsAsync(int userId)
    {
        try
        {
            GetAllLikedCommentsResponse response =
                await client.GetAllLikedCommentsAsync(
                    new GetAllLikedCommentsRequest
                    {
                        UserId = userId
                    });
            string json = response.List;
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                WriteIndented = true,
                PropertyNameCaseInsensitive = true
            };
            List<DTOs.Models.Comment> comments = JsonSerializer.Deserialize<List<DTOs.Models.Comment>>(json, options);
            return comments;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw new Exception("Error getting all liked comments");
        }
    }
}