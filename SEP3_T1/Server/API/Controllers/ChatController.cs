using App.LogicInterfaces;
using DTOs.DTOs;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ChatController : ControllerBase
    {
        private readonly IChatLogic _chatLogic;

        public ChatController(IChatLogic chatLogic)
        {
            _chatLogic = chatLogic;
        }

        [HttpPost]
        public async Task<ActionResult> SendMessage([FromBody] SendMessageDTO dto)
        {
            try
            {
                await _chatLogic.SendMessageAsync(dto);
                return Ok();
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
        }

        [HttpGet]
        public async Task<ActionResult> GetAllMessages()
        {
            try
            {
                var result = await _chatLogic.GetAllMessagesAsync();
                return Ok(result);
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
        }

        [HttpGet("{sender:int}/{receiver:int}")]
        public async Task<ActionResult> GetMessages([FromRoute] int sender, [FromRoute] int receiver)
        {
            try
            {
                var result = await _chatLogic.GetMessagesAsync(sender, receiver);
                return Ok(result);
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
        }
    }
}