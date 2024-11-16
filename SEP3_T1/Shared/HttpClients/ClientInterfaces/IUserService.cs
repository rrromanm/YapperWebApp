﻿using DTOs.User;

namespace HttpClients.ClientInterfaces;
using Microsoft.AspNetCore.Mvc;

public interface IUserService
{
    Task CreateSMUser(CreateUserDTO dto);
    Task UpdateSMUser(UpdateUserDTO dto);
    Task DeleteSMUser(int accountId);
}