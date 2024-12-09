package sep3.dao;

import sep3.dto.report.PostReportDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportDAO implements ReportDAOInterface {

    private static ReportDAO instance;

    private ReportDAO() {}

    public static ReportDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }
    @Override
    public void sendReport(int userID, int postID) throws SQLException {
        try(Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.reported_post (userid, postid) VALUES (?,?)");
            statement.setInt(1, userID);
            statement.setInt(2, postID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to send report");

        }
    }

    @Override
    public ArrayList<PostReportDTO> getReports() throws SQLException {
        try(Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.reported_post");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostReportDTO> reports = new ArrayList<>();
            while (resultSet.next()) {
                reports.add(new PostReportDTO(
                        resultSet.getInt("userid"),
                        resultSet.getInt("postid"),
                        resultSet.getString("date")));
            }
            return reports;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get reports");
        }
    }
}
