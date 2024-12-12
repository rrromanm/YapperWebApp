using App.Logic;
using DTOs.DTOs;
using NUnit.Framework;

namespace Logic;

public class ChatLogicTests
{
    private ChatLogic chatLogic;
    
    [SetUp]
    public void Setup()
    {
        chatLogic = new ChatLogic(new GRPCService());
    }
    
    [Test]
    public async Task sending_message_sends_message()
    {
        SendMessageDTO dto = new SendMessageDTO("Test", 1, 2);
        Assert.DoesNotThrowAsync(() => chatLogic.SendMessageAsync(dto));
        List<MessageDTO> messages = await chatLogic.GetMessagesAsync(1, 2);
        Assert.That(messages.Count, Is.EqualTo(1));
        Assert.That(messages[0].Message, Is.EqualTo("Test"));
        Assert.That(messages[0].SenderId, Is.EqualTo(1));
        Assert.That(messages[0].ReceiverId, Is.EqualTo(2));
    }
    
    [Test]
    public async Task getting_messages_gets_messages()
    {
        SendMessageDTO dto = new SendMessageDTO("Test", 1, 2);
        await chatLogic.SendMessageAsync(dto);
        List<MessageDTO> messages = await chatLogic.GetMessagesAsync(1, 2);
        Assert.That(messages.Count, Is.EqualTo(1));
        Assert.That(messages[0].Message, Is.EqualTo("Test"));
        Assert.That(messages[0].SenderId, Is.EqualTo(1));
        Assert.That(messages[0].ReceiverId, Is.EqualTo(2));
    }
    
    [Test]
    public async Task getting_all_messages_gets_all_messages()
    {
        SendMessageDTO dto = new SendMessageDTO("Test", 1, 2);
        await chatLogic.SendMessageAsync(dto);
        List<MessageDTO> messages = await chatLogic.GetAllMessagesAsync();
        Assert.That(messages.Count, Is.GreaterThan(0));
    }
}