package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import report.*;
import sep3.dao.ReportDAOInterface;
import sep3.dto.report.PostReportDTO;

import java.util.ArrayList;

public class ReportImpl extends ReportServiceGrpc.ReportServiceImplBase {
    private ReportDAOInterface dao;
    private final Gson gson;

    public ReportImpl(ReportDAOInterface dao) {
        this.dao = dao;
        this.gson = new Gson();
    }

    /**
     * Sends a report for a post, identified by the user and post IDs.
     *
     * @param request The request containing the user ID and post ID to report.
     * @param responseObserver The stream observer to send the response back.
     */
    @Override
    public void sendReport(SendReportRequest request, StreamObserver<SendReportEmptyResponse> responseObserver) {
        try {
            dao.sendReport(request.getUserid(), request.getPostid());
            responseObserver.onNext(SendReportEmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Retrieves all reports.
     *
     * @param request The request to fetch all reports.
     * @param responseObserver The stream observer to send the list of reports back.
     */
    @Override
    public void getReports(GetReportsRequest request, StreamObserver<GetReportsResponse> responseObserver) {
        try {
            ArrayList<PostReportDTO> reports = dao.getReports();

            String string = gson.toJson(reports);

            GetReportsResponse response = GetReportsResponse.newBuilder().setList(string).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Rejects a report based on the provided report ID.
     *
     * @param request The request containing the report ID to reject.
     * @param responseObserver The stream observer to send the response back.
     */
    @Override
    public void rejectReport(RejectReportRequest request, StreamObserver<SendReportEmptyResponse> responseObserver) {
        try {
            dao.rejectReport(request.getReportid());
            responseObserver.onNext(SendReportEmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
