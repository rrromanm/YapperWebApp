using App.LogicInterfaces;
using DTOs.DTOs.Report;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("[controller]")]
public class ReportController : ControllerBase
{
    private readonly IReportLogic _reportLogic;
    
    public ReportController(IReportLogic reportLogic)
    {
        _reportLogic = reportLogic;
    }
    
    [HttpPost]
    public async Task<ActionResult> SendReport(ReportDTO dto)
    {
        try
        {
            await _reportLogic.SendReport(dto);
            return Ok();
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
    [HttpGet]
    public async Task<ActionResult> GetAllReports()
    {
        try
        {
            List<ReportDTO> reports = await _reportLogic.GetAllReports();
            return Ok(reports);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
}