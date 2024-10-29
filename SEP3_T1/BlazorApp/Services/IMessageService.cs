using API;
using Microsoft.AspNetCore.Mvc;

namespace BlazorApp.Services;

public interface IMessageService
{
    public Task<IActionResult> SendMessage(SendMessage message);
}