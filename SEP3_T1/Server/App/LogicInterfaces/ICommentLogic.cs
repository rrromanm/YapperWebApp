using DTOs.DTOs;

namespace App.LogicInterfaces;

public interface ICommentLogic
{
    Task CreateComment(CreateCommentDTO dto);
    Task UpdateComment(UpdateCommentDTO dto);
}