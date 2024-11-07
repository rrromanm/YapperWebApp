using App.LogicInterfaces;
using DTOs.DTOs;
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
    
    public async Task CreateComment(CreateCommentDTO dto)
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
    public async Task UpdateComment(UpdateCommentDTO dto)
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
}