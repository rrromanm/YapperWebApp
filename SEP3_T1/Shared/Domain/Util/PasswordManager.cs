using System.Security.Cryptography;
using System.Text;

namespace DTOs.Util;

using Microsoft.AspNetCore.Identity;

public class PasswordManager
{
    public string HashPassword(string password)
    {
        using (SHA256 sha256 = SHA256.Create())
        {
            byte[] passwordBytes = Encoding.UTF8.GetBytes(password);
            byte[] hashBytes = sha256.ComputeHash(passwordBytes);

            return Convert.ToBase64String(hashBytes);
        }
    }

    public bool VerifyPassword(string plainTextPassword, string hashedPassword)
    {
        string computedHash = HashPassword(plainTextPassword);
        return computedHash == hashedPassword;
    }
}

