package sep3.dao;

import sep3.dto.categoryRequest.CategoryRequestDTO;
import sep3.dto.categoryRequest.CreateCategoryRequestDTO;
import sep3.util.DatabaseConnectionManager;
import sep3.util.YapDate;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CategoryRequestDAO implements CategoryRequestDAOInterface
{
  private static CategoryRequestDAO instance;
  private CategoryRequestDAO() {}

  public static CategoryRequestDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new CategoryRequestDAO();
    }
    return instance;
  }
  private String formatTimestamp(Timestamp timestamp) {
    LocalDateTime categoryRequestDateTime = timestamp.toLocalDateTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return categoryRequestDateTime.format(formatter);
  }

  @Override public void createCategoryRequest(CreateCategoryRequestDTO dto) throws SQLException
  {
    try (Connection connection = DatabaseConnectionManager.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO category_request (categoryName, userId) VALUES (?,?)");
      statement.setString(1, dto.getCategoryName());
      statement.setInt(2, dto.getUserId());
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      throw new SQLException("Failed to create a category request", e);
    }
  }


  @Override public void deleteCategoryRequest(int id) throws SQLException
  {
    try(Connection connection = DatabaseConnectionManager.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM category_request WHERE requestId = ?");
      statement.setInt(1, id);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      throw new SQLException("Failed to delete a category request", e);
    }
  }

  @Override public CategoryRequestDTO getCategoryRequest(int id) throws SQLException
  {
    try(Connection connection = DatabaseConnectionManager.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM category_request WHERE requestid = ?");
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();

      if(resultSet.next()){
        String formattedDateTime = new YapDate(resultSet.getTimestamp("date")).toString();

        return new CategoryRequestDTO(
          formattedDateTime,
          resultSet.getString("categoryName"),
          resultSet.getInt("userId"),
          resultSet.getInt("requestId")
        );
      }
      else
      {
        throw new SQLException("Category request not found");
      }
    }
    catch (SQLException e)
    {
      throw new SQLException("Failed to get a category request", e);
    }
  }

  @Override public ArrayList<CategoryRequestDTO> getAllCategoryRequests() throws SQLException
  {
    try(Connection connection = DatabaseConnectionManager.getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM category_request");
      ResultSet resultSet = statement.executeQuery();
      ArrayList<CategoryRequestDTO> categoryRequests = new ArrayList<>();

      while(resultSet.next()){
        String formattedDateTime = new YapDate(resultSet.getTimestamp("date")).toString();

        categoryRequests.add(new CategoryRequestDTO(
          formattedDateTime,
          resultSet.getString("categoryName"),
          resultSet.getInt("userId"),
          resultSet.getInt("requestId")
        ));
      }
      return categoryRequests;
    }
    catch (SQLException e)
    {
      throw new SQLException("Failed to get all category requests", e);
    }
  }

  @Override public ArrayList<CategoryRequestDTO> getCategoryRequestsByName(String name) throws SQLException
  {
    try(Connection connection = DatabaseConnectionManager.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM category_request WHERE categoryName ILIKE ?");
      statement.setString(1, "%" + name + "%");
      ResultSet resultSet = statement.executeQuery();
      ArrayList<CategoryRequestDTO> categoryRequests = new ArrayList<>();

      while(resultSet.next()){
        String formattedDateTime = new YapDate(resultSet.getTimestamp("date")).toString();

        categoryRequests.add(new CategoryRequestDTO(
            formattedDateTime,
            resultSet.getString("categoryName"),
            resultSet.getInt("userId"),
            resultSet.getInt("requestId")
        ));
      }
      return categoryRequests;
    }
  }

  @Override public void approveCategoryRequest(String categoryName, int addedBy) throws SQLException
  {
    try(Connection connection = DatabaseConnectionManager.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO category (name, addedBy) VALUES (?,?)");
      statement.setString(1, categoryName);
      statement.setInt(2, addedBy);
      statement.executeUpdate();

      disapproveCategoryRequest(categoryName);
    }
    catch (SQLException e)
    {
      throw new SQLException("Failed to approve a category request", e);
    }
  }

  @Override public void disapproveCategoryRequest(String categoryName) throws SQLException
  {
    try(Connection connection = DatabaseConnectionManager.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM category_request WHERE categoryName = ?");
      statement.setString(1, categoryName);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      throw new SQLException("Failed to disapprove a category request", e);
    }
  }
}
