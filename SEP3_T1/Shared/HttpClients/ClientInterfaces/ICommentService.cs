using DTOs.DTOs;
using DTOs.DTOs.Comment;
using DTOs.Models;

namespace HttpClients.ClientInterfaces;

public interface ICommentService
{
    Task CreateCommentAsync(CreateCommentDTO dto);
    Task UpdateCommentAsync(UpdateCommentDTO dto);
    Task DeleteCommentAsync(int id);
    Task<Comment> GetCommentAsync(int id);
    Task<List<Comment>> GetAllCommentsAsync();
    Task<List<Comment>> GetCommentsByPostIdAsync(int postId);
    Task<List<Comment>> GetCommentsByUserIdAsync(int userId);
    Task LikeCommentAsync(int commentId, int userId);
    Task UnlikeCommentAsync(int commentId, int userId);
}