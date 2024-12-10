using DTOs.DTOs.Report;

namespace HttpClients.ClientInterfaces;

public interface IReportService
{
    Task SendReport(ReportDTO dto);
    Task<List<ReportDTO>> GetAllReports();
    Task RejectReport(int reportid);
}