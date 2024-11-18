using DTOs.User;

namespace App.LogicInterfaces;

public interface ISMUserLogic
{
    Task CreateSMUser(CreateUserDTO dto);
    Task UpdateSMUser(UpdateUserDTO dto);
    Task DeleteUser(int accountId);
}