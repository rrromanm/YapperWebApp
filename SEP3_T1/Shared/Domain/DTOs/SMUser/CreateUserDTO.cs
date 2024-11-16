﻿namespace DTOs.User;

public class CreateUserDTO
{
    public string Password { get; set; }
    public string Email { get; set; }
    public string Username { get; set; }
    public string Nickname { get; set; }
    
    public CreateUserDTO(string username, string password, string email, string nickname)
    {
        Username = username;
        Nickname = nickname;
        Password = password;
        Email = email;
    }
}