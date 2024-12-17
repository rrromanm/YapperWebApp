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

    /**
     * Returns the singleton instance of {@code ReportDAO}.
     *
     * @return the singleton instance of {@code ReportDAO}
     * @throws SQLException if an error occurs while retrieving the instance
     */
    public static ReportDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }

    /**
     * Sends a report for a specific post by a user.
     *
     * @param userID The ID of the user reporting the post.
     * @param postID The ID of the post being reported.
     * @throws SQLException if an error occurs while executing the SQL statement to insert the report.
     */
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

    /**
     * Retrieves all reported posts from the database.
     *
     * @return An ArrayList of {@link PostReportDTO} objects representing the reported posts.
     * @throws SQLException if an error occurs while retrieving the report data from the database.
     */
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
                        resultSet.getString("date"),
                        resultSet.getInt("reportid")));
            }
            return reports;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get reports");
        }
    }

    /**
     * Rejects a report by removing it from the database.
     *
     * @param reportId The ID of the report to be rejected.
     * @throws SQLException if an error occurs while executing the SQL statement to delete the report.
     */
    @Override
    public void rejectReport(int reportId) throws SQLException {
        try(Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.reported_post WHERE reportid = ?");
            statement.setInt(1, reportId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to reject report");
        }
    }
}
