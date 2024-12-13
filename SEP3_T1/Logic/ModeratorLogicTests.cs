using App.Logic;
using DTOs.Models;
using NUnit.Framework;

namespace Logic;

public class ModeratorLogicTests
{
    private ModeratorLogic moderatorLogic;
    [SetUp]
    public void Setup()
    {
        moderatorLogic = new ModeratorLogic(new GRPCService());
    }
    
    [Test]
    public async Task getting_moderator_by_username_returns_correct_moderator()
    {
        Moderator dto = new Moderator();
        dto.Username = "Test";
        dto.Password = "Test";
        Moderator moderator = await moderatorLogic.GetModeratorByUsernameAsync("Test");
        Assert.That(moderator.Username, Is.EqualTo("Test"));
        Assert.That(moderator.Password, Is.EqualTo("Test"));
    }
}