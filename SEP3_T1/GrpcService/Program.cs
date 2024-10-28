using Grpc.Net.Client;
using GrpcService.Protos;


namespace GrpcService;

class Program
{
    static async Task Main(string[] args)
    {
        using var channel = GrpcChannel.ForAddress("http://localhost:9090");
        var client = new TextConverter.TextConverterClient(channel);
        var reply = await client.ToUpperAsync(
            new RequestText { InputText = "Hello World!" }
        );
        Console.WriteLine("Greeting: " + reply.OutputText);
        Console.WriteLine("Press any key to exit...");
        Console.ReadKey();
    }
}