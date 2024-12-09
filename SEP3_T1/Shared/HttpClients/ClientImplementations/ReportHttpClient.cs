using System.Net.Http.Json;
using DTOs.DTOs.Report;
using HttpClients.ClientInterfaces;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;
using JsonSerializerOptions = System.Text.Json.JsonSerializerOptions;

namespace HttpClients.ClientImplementations;

public class ReportHttpClient : IReportService
{
    private readonly HttpClient _client;
    
    public ReportHttpClient(HttpClient client)
    {
        _client = client;
    }
    
    public async Task SendReport(ReportDTO dto)
    {
        HttpResponseMessage response = await _client.PostAsJsonAsync("/Report", dto);
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
    }

    public async Task<List<ReportDTO>> GetAllReports()
    {
        HttpResponseMessage response = await _client.GetAsync("/Report");
        string content = await response.Content.ReadAsStringAsync();
        
        if (!response.IsSuccessStatusCode)
        {
            string e = await response.Content.ReadAsStringAsync();
            throw new Exception(e);
        }
        return JsonSerializer.Deserialize<List<ReportDTO>>(content, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
    }
}