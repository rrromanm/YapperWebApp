using DTOs.DTOs;

namespace HttpClients.ClientInterfaces;

public interface ICommentService
{
    Task CreateCommentAsync(CreateCommentDTO dto);
    Task UpdateCommentAsync(UpdateCommentDTO dto);
}