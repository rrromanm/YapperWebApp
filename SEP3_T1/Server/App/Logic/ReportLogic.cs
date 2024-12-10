using System.Text.Json;
using App.LogicInterfaces;
using DTOs.DTOs.Report;
using Grpc.Net.Client;
using GrpcClient;

namespace App.Logic;

public class ReportLogic : IReportLogic
{
    private ReportService.ReportServiceClient _client;
    
    public ReportLogic(GRPCService service)
    {
        GrpcChannel channel = service.Channel;
        _client = new ReportService.ReportServiceClient(channel);
    }
    
    
    public async Task SendReport(ReportDTO dto)
    {
        try
        {
            await _client.SendReportAsync(new SendReportRequest
            {
                Userid = dto.UserId,
                Postid = dto.PostId
            });
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<List<ReportDTO>> GetAllReports()
    {
        try
        {
            GetReportsResponse response = await _client.GetReportsAsync(new GetReportsRequest());

            string json = response.List;
            
            JsonSerializerOptions options = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            };
            List<ReportDTO> reports = JsonSerializer.Deserialize<List<ReportDTO>>(json, options);
            return reports;

        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task RejectReport(int reportid)
    {
        try
        {
            await _client.RejectReportAsync(new RejectReportRequest
            {
                Reportid = reportid
            });
            Console.WriteLine("report rejected with id: " + reportid);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}