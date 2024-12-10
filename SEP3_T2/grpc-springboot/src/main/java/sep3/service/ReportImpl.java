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
}
