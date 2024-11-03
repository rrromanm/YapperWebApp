using DTOs.User;

namespace App.LogicInterfaces;

public interface ISMUserLogic
{
    Task CreateSMUser(CreateUserDTO dto);
    Task UpdateEmail(UpdateUserDTO dto);
    Task UpdateNickname(UpdateUserDTO dto);
    Task UpdatePassword(UpdateUserDTO dto);
    Task DeleteUser(int accountId);
}