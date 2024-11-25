
using DTOs.DTOs.Comment;

namespace App.LogicInterfaces;

public interface ICommentLogic
{
    Task CreateCommentAsync(CreateCommentDTO dto);
    Task UpdateCommentAsync(UpdateCommentDTO dto);
    Task DeleteCommentAsync(int id);
    Task<DTOs.Models.Comment> GetCommentAsync(int id);
    Task<List<DTOs.Models.Comment>> GetAllCommentsAsync();
    Task<List<DTOs.Models.Comment>> GetCommentsByPostIdAsync(int postId);
    Task<List<DTOs.Models.Comment>> GetCommentsByUserIdAsync(int userId);
    Task LikeCommentAsync(int commentId, int userId);
    Task UnlikeCommentAsync(int commentId, int userId);
}