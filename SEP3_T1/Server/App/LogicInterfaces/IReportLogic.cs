using DTOs.DTOs.Report;

namespace App.LogicInterfaces;

public interface IReportLogic
{
    Task SendReport(ReportDTO dto);
    Task<List<ReportDTO>> GetAllReports();
    Task RejectReport(int reportid);
}