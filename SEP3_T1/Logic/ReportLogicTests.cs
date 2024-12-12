using App.Logic;
using DTOs.DTOs.Report;
using NUnit.Framework;

namespace Logic;

public class ReportLogicTests
{
    private ReportLogic reportLogic;
    
    [SetUp]
    public void Setup()
    {
        reportLogic = new ReportLogic(new GRPCService());
    }
    
    [Test]
    public async Task sending_report_sends_report()
    {
        ReportDTO dto = new ReportDTO();
        dto.UserId = 1;
        dto.PostId = 1;
        Assert.DoesNotThrowAsync(() => reportLogic.SendReport(dto));
        List<ReportDTO> reports = await reportLogic.GetAllReports();
        Assert.That(reports.Count, Is.EqualTo(1));
        Assert.That(reports[0].UserId, Is.EqualTo(1));
        Assert.That(reports[0].PostId, Is.EqualTo(1));
    }
    
    [Test]
    public async Task getting_reports_gets_reports()
    {
        ReportDTO dto = new ReportDTO();
        dto.UserId = 1;
        dto.PostId = 1;
        await reportLogic.SendReport(dto);
        List<ReportDTO> reports = await reportLogic.GetAllReports();
        Assert.That(reports.Count, Is.EqualTo(1));
        Assert.That(reports[0].UserId, Is.EqualTo(1));
        Assert.That(reports[0].PostId, Is.EqualTo(1));
    }
    
    [Test]
    public async Task rejecting_report_rejects_report()
    {
        ReportDTO dto = new ReportDTO();
        dto.UserId = 1;
        dto.PostId = 1;
        await reportLogic.SendReport(dto);
        List<ReportDTO> reports = await reportLogic.GetAllReports();
        Assert.That(reports.Count, Is.EqualTo(1));
        Assert.That(reports[0].UserId, Is.EqualTo(1));
        Assert.That(reports[0].PostId, Is.EqualTo(1));
        Assert.DoesNotThrowAsync(() => reportLogic.RejectReport(reports[0].ReportId));
        reports = await reportLogic.GetAllReports();
        Assert.That(reports.Count, Is.EqualTo(0));
    }
}