using App.Logic;
using DTOs.Models;
using DTOs.User;
using NUnit.Framework;

namespace Logic;

public class SMUserLogicTests
{
    private SMUserLogic smUserLogic;
    [SetUp]
    public void Setup()
    {
        smUserLogic = new SMUserLogic(new GRPCService());
    }
    
    [Test]
    public async Task creating_smusers_adds_it_to_database()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        Assert.DoesNotThrowAsync(() => smUserLogic.CreateSMUser(dto));
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        Assert.That(user.Username, Is.EqualTo("Test"));
        Assert.That(user.Email, Is.EqualTo("Test"));
        Assert.That(user.Nickname, Is.EqualTo("Test"));
        Assert.That(user.Password, Is.EqualTo("Test"));
    }
    
    [Test]
    public async Task updating_email_updates_it_in_database()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        UpdateUserDTO updateDto = new UpdateUserDTO();
        updateDto.UserId = user.Id;
        updateDto.Email = "Test2";
        Assert.DoesNotThrowAsync(() => smUserLogic.UpdateEmail(updateDto));
        SMUser updatedUser = await smUserLogic.GetByUsernameAsync("Test");
        Assert.That(updatedUser.Email, Is.EqualTo("Test2"));
        Assert.That(updatedUser.Username, Is.EqualTo("Test"));
        Assert.That(updatedUser.Nickname, Is.EqualTo("Test"));
        Assert.That(updatedUser.Password, Is.EqualTo("Test"));
    }

    [Test]
    public async Task updating_password_updates_it_in_database()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        UpdateUserDTO updateDto = new UpdateUserDTO();
        updateDto.UserId = user.Id;
        updateDto.Password = "Test2";
        Assert.DoesNotThrowAsync(() => smUserLogic.UpdatePassword(updateDto));
        SMUser updatedUser = await smUserLogic.GetByUsernameAsync("Test");
        Assert.That(updatedUser.Email, Is.EqualTo("Test"));
        Assert.That(updatedUser.Username, Is.EqualTo("Test"));
        Assert.That(updatedUser.Nickname, Is.EqualTo("Test"));
        Assert.That(updatedUser.Password, Is.EqualTo("Test2"));
    }

    [Test]
    public async Task updating_nickname_updates_it_in_database()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        UpdateUserDTO updateDto = new UpdateUserDTO();
        updateDto.UserId = user.Id;
        updateDto.Nickname = "Test2";
        Assert.DoesNotThrowAsync(() => smUserLogic.UpdateNickname(updateDto));
        SMUser updatedUser = await smUserLogic.GetByUsernameAsync("Test");
        Assert.That(updatedUser.Email, Is.EqualTo("Test"));
        Assert.That(updatedUser.Username, Is.EqualTo("Test"));
        Assert.That(updatedUser.Nickname, Is.EqualTo("Test2"));
        Assert.That(updatedUser.Password, Is.EqualTo("Test"));
    }
    
    [Test]
    public async Task deleting_smusers_deletes_it_from_database()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        Assert.DoesNotThrowAsync(() => smUserLogic.DeleteUser(user.Id));
        Assert.ThrowsAsync<Exception>(() => smUserLogic.GetByUsernameAsync("Test"));
    }

    [Test]
    public async Task getting_all_smusers_returns_all_smusers()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        CreateUserDTO dto2 = new CreateUserDTO();
        dto2.Username = "Test2";
        dto2.Password = "Test2";
        dto2.Email = "Test2";
        dto2.Nickname = "Test2";
        await smUserLogic.CreateSMUser(dto2);
        List<SMUser> users = await smUserLogic.GetAllUsers();
        Assert.That(users.Count, Is.EqualTo(2));
    }
    
    [Test]
    public async Task getting_smuser_by_username_returns_correct_smuser()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        Assert.That(user.Username, Is.EqualTo("Test"));
        Assert.That(user.Email, Is.EqualTo("Test"));
        Assert.That(user.Nickname, Is.EqualTo("Test"));
        Assert.That(user.Password, Is.EqualTo("Test"));
    }

    [Test]
    public async Task getting_smuser_by_id_returns_correct_smuser()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        SMUser userById = await smUserLogic.GetByIdAsync(user.Id);
        Assert.That(userById.Username, Is.EqualTo("Test"));
        Assert.That(userById.Email, Is.EqualTo("Test"));
        Assert.That(userById.Nickname, Is.EqualTo("Test"));
        Assert.That(userById.Password, Is.EqualTo("Test"));
    }
    
    [Test]
    public async Task following_user_increases_following_count()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        CreateUserDTO dto2 = new CreateUserDTO();
        dto2.Username = "Test2";
        dto2.Password = "Test2";
        dto2.Email = "Test2";
        dto2.Nickname = "Test2";
        await smUserLogic.CreateSMUser(dto2);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        SMUser user2 = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.DoesNotThrowAsync(() => smUserLogic.FollowUser(user.Id, user2.Id));
        SMUser updatedUser = await smUserLogic.GetByUsernameAsync("Test");
        Assert.That(updatedUser.followingCount, Is.EqualTo(1));
    }

    [Test]
    public async Task unfollowing_user_decreases_following_count()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        CreateUserDTO dto2 = new CreateUserDTO();
        dto2.Username = "Test2";
        dto2.Password = "Test2";
        dto2.Email = "Test2";
        dto2.Nickname = "Test2";
        await smUserLogic.CreateSMUser(dto2);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        SMUser user2 = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.DoesNotThrowAsync(() => smUserLogic.FollowUser(user.Id, user2.Id));
        Assert.DoesNotThrowAsync(() => smUserLogic.UnfollowUser(user.Id, user2.Id));
        SMUser updatedUser = await smUserLogic.GetByUsernameAsync("Test");
        Assert.That(updatedUser.followingCount, Is.EqualTo(0));
    }

    [Test]
    public async Task following_user_increases_followers_count()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        CreateUserDTO dto2 = new CreateUserDTO();
        dto2.Username = "Test2";
        dto2.Password = "Test2";
        dto2.Email = "Test2";
        dto2.Nickname = "Test2";
        await smUserLogic.CreateSMUser(dto2);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        SMUser user2 = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.DoesNotThrowAsync(() => smUserLogic.FollowUser(user.Id, user2.Id));
        SMUser updatedUser = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.That(updatedUser.followersCount, Is.EqualTo(1));
    }

    [Test]
    public async Task unfollowing_user_decreases_followers_count()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        CreateUserDTO dto2 = new CreateUserDTO();
        dto2.Username = "Test2";
        dto2.Password = "Test2";
        dto2.Email = "Test2";
        dto2.Nickname = "Test2";
        await smUserLogic.CreateSMUser(dto2);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        SMUser user2 = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.DoesNotThrowAsync(() => smUserLogic.FollowUser(user.Id, user2.Id));
        Assert.DoesNotThrowAsync(() => smUserLogic.UnfollowUser(user.Id, user2.Id));
        SMUser updatedUser = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.That(updatedUser.followersCount, Is.EqualTo(0));
    }
    
    [Test]
    public async Task getting_followers_returns_correct_followers()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        CreateUserDTO dto2 = new CreateUserDTO();
        dto2.Username = "Test2";
        dto2.Password = "Test2";
        dto2.Email = "Test2";
        dto2.Nickname = "Test2";
        await smUserLogic.CreateSMUser(dto2);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        SMUser user2 = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.DoesNotThrowAsync(() => smUserLogic.FollowUser(user.Id, user2.Id));
        List<FollowersDTO> followers = await smUserLogic.GetFollowers(user2.Id);
        Assert.That(followers.Count, Is.EqualTo(1));
    }

    [Test]
    public async Task getting_following_returns_correct_following()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        CreateUserDTO dto2 = new CreateUserDTO();
        dto2.Username = "Test2";
        dto2.Password = "Test2";
        dto2.Email = "Test2";
        dto2.Nickname = "Test2";
        await smUserLogic.CreateSMUser(dto2);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        SMUser user2 = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.DoesNotThrowAsync(() => smUserLogic.FollowUser(user.Id, user2.Id));
        List<FollowersDTO> following = await smUserLogic.GetFollowing(user.Id);
        Assert.That(following.Count, Is.EqualTo(1));
    }
    
    [Test]
    public async Task getting_if_smuser_follows_another()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        CreateUserDTO dto2 = new CreateUserDTO();
        dto2.Username = "Test2";
        dto2.Password = "Test2";
        dto2.Email = "Test2";
        dto2.Nickname = "Test2";
        await smUserLogic.CreateSMUser(dto2);
        SMUser user = await smUserLogic.GetByUsernameAsync("Test");
        SMUser user2 = await smUserLogic.GetByUsernameAsync("Test2");
        Assert.DoesNotThrowAsync(() => smUserLogic.FollowUser(user.Id, user2.Id));
        bool follows = await smUserLogic.IsFollowing(user.Id, user2.Id);
        Assert.That(follows, Is.EqualTo(true));
    }

    [Test]
    public async Task getting_smuser_by_search()
    {
        CreateUserDTO dto = new CreateUserDTO();
        dto.Username = "Test";
        dto.Password = "Test";
        dto.Email = "Test";
        dto.Nickname = "Test";
        await smUserLogic.CreateSMUser(dto);
        List<FollowersDTO> users = await smUserLogic.GetUsersBySearch("Test");
        Assert.That(users.Count, Is.EqualTo(1));
    }


}