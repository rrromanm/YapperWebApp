using BlazorApp.Auth;
using HttpClients.ClientImplementations;
using HttpClients.ClientInterfaces;
using Microsoft.AspNetCore.Components.Authorization;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddScoped(
    sp => new HttpClient
    {
        BaseAddress = new Uri("https://localhost:7211")
    });

builder.Services.AddScoped<ISMUserService, SMUserHttpClient>();
builder.Services.AddScoped<IPostService, PostHttpClient>();
builder.Services.AddScoped<ICommentService, CommentHttpClient>();
builder.Services.AddScoped<ICategoryService, CategoryHttpClient>();
builder.Services.AddScoped<IChatService, ChatHttpClient>();
builder.Services.AddScoped<INotificationService, NotificationHttpClient>();
builder.Services.AddScoped<AuthenticationStateProvider, SimpleAuthProvider>();
builder.Services.AddScoped<ICategoryRequestService, CategoryRequestHttpClient>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
}

// To avoid the HTTPS port error, ensure HTTPS redirection is only used if the app supports it.
if (app.Configuration.GetValue<bool>("UseHttpsRedirection", false)) 
{
    app.UseHttpsRedirection();
}


app.UseStaticFiles();
app.UseAntiforgery();

app.MapRazorComponents<BlazorApp.Components.App>()
    .AddInteractiveServerRenderMode();

app.Run();