using Grpc.Core;
using Grpc.Net.Client;

namespace App.Logic;

public class GRPCService
{
    public GrpcChannel Channel;
    
    public GRPCService()
    {
        Channel = GrpcChannel.ForAddress("http://localhost:8080", new GrpcChannelOptions
        {
            Credentials = ChannelCredentials.Insecure
        });
        
        
        
    }
}