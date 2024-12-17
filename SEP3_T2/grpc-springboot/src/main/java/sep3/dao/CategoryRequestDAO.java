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

  /**
   * Private constructor to prevent direct instantiation.
   * Use {@link #getInstance()} to get the singleton instance.
   */
  private CategoryRequestDAO() {}

  /**
   * Retrieves the singleton instance of {@code CategoryRequestDAO}.
   *
   * @return the singleton instance of {@code CategoryRequestDAO}
   * @throws SQLException if a database access error occurs
   */
  public static CategoryRequestDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new CategoryRequestDAO();
    }
    return instance;
  }

  /**
   * Creates a new category request in the database.
   *
   * @param dto the {@link CreateCategoryRequestDTO} object containing the category request details
   * @throws SQLException if there is an error during the insert operation
   */
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

  /**
   * Deletes a category request from the database by its ID.
   *
   * @param id the ID of the category request to be deleted
   * @throws SQLException if there is an error during the delete operation
   */
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

  /**
   * Retrieves a category request by its ID from the database.
   *
   * @param id the ID of the category request to retrieve
   * @return a {@link CategoryRequestDTO} object representing the category request, or {@code null} if not found
   * @throws SQLException if there is an error during the retrieval
   */
  @Override public CategoryRequestDTO getCategoryRequest(int id) throws SQLException
  {
    try(Connection connection = DatabaseConnectionManager.getConnection())
    {
      CategoryRequestDTO categoryRequest = null;

      PreparedStatement statement = connection.prepareStatement("SELECT * FROM category_request WHERE requestid = ?");
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()){
        String formattedDateTime = new YapDate(resultSet.getTimestamp("date")).toString();

        categoryRequest = new CategoryRequestDTO(
                formattedDateTime,
                resultSet.getString("categoryName"),
                resultSet.getInt("userId"),
                resultSet.getInt("requestId")
        );
      }
      return categoryRequest;
    }
    catch (SQLException e)
    {
      throw new SQLException("Failed to get a category request", e);
    }
  }

  /**
   * Retrieves all category requests from the database.
   *
   * @return an {@link ArrayList} of {@link CategoryRequestDTO} objects representing all category requests
   * @throws SQLException if there is an error during the retrieval
   */
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


  /**
   * Retrieves category requests from the database by category name.
   * Uses a case-insensitive search with partial matches.
   *
   * @param name the name of the category to search for
   * @return an {@link ArrayList} of {@link CategoryRequestDTO} objects matching the search criteria
   * @throws SQLException if there is an error during the retrieval
   */
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

  /**
   * Approves a category request by adding the category to the main category table.
   * Once approved, the request is automatically disapproved (i.e., deleted).
   *
   * @param categoryName the name of the category to approve
   * @param addedBy the ID of the user who approved the request
   * @throws SQLException if there is an error during the approval or disapproval process
   */
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

  /**
   * Disapproves (deletes) a category request by its category name.
   *
   * @param categoryName the name of the category request to disapprove
   * @throws SQLException if there is an error during the delete operation
   */
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
