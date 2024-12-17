package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import moderator.GetModeratorRequest;
import moderator.ModeratorResponse;
import moderator.ModeratorServiceGrpc;
import sep3.dao.ModeratorDAOInterface;
import sep3.dto.moderator.ModeratorDTO;

public class ModeratorImpl extends ModeratorServiceGrpc.ModeratorServiceImplBase
{
    private ModeratorDAOInterface dao;
    private final Gson gson;

    public ModeratorImpl(ModeratorDAOInterface dao)
    {
        this.dao = dao;
        this.gson = new Gson();
    }

    /**
     * Retrieves the moderator details by their username.
     *
     * @param request The request containing the username of the moderator to be fetched.
     * @param responseObserver The stream observer to send the moderator response.
     */
    @Override
    public void getModeratorByUserName(GetModeratorRequest request, StreamObserver<ModeratorResponse> responseObserver) {
        try {
            ModeratorDTO moderator = dao.getModeratorByUserName(request.getUsername());
            ModeratorResponse response = ModeratorResponse.newBuilder()
                .setId(moderator.getId())
                .setUsername(moderator.getUsername())
                .setPassword(moderator.getPassword())
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
