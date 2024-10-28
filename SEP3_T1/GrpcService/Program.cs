using Grpc.Net.Client;
using GrpcService.Protos;

namespace GrpcService;

class Program
{
    static async Task Main(string[] args)
    {
        using var channel = GrpcChannel.ForAddress("http://localhost:8080");
        var client = new TextConverter.TextConverterClient(channel);
        var reply = await client.ToUpperAsync(
            new RequestText { InputText = "Hello World!" }
        );
        Console.WriteLine("Upper case: " + reply.OutputText);
        Console.WriteLine("Press any key to exit...");
        Console.ReadKey();
        
    }
}