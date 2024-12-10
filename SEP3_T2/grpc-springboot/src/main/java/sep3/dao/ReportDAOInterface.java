package sep3.dao;

import sep3.dto.report.PostReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReportDAOInterface {
    void sendReport(int userID, int postID) throws SQLException;
    ArrayList<PostReportDTO> getReports() throws SQLException;

}
