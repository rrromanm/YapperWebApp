package sep3.dao;

import sep3.dto.category.CategoryDTO;
import sep3.dto.category.CreateCategoryDTO;
import sep3.dto.category.UpdateCategoryDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO implements CategoryDAOInterface {

    private static CategoryDAO instance;

    /**
     * Private constructor to prevent direct instantiation.
     * Use {@link #getInstance()} to get the singleton instance.
     */
    private CategoryDAO() {
    }

    /**
     * Retrieves the singleton instance of {@code CategoryDAO}.
     *
     * @return the singleton instance of {@code CategoryDAO}
     * @throws SQLException if a database access error occurs
     */
    public static CategoryDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    /**
     * Creates a new category in the database.
     *
     * @param dto the {@link CreateCategoryDTO} object containing the category details
     * @throws SQLException if there is an error during the insert operation
     */
    @Override
    public void createCategory(CreateCategoryDTO dto) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.category (name, addedBy) VALUES (?,?)");
            statement.setString(1, dto.getName());
            statement.setInt(2, dto.getAddedBy());
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to create category");
        }
    }

    /**
     * Updates an existing category in the database.
     *
     * @param dto the {@link UpdateCategoryDTO} object containing the updated category details
     * @throws SQLException if there is an error during the update operation
     */
    @Override
    public void updateCategory(UpdateCategoryDTO dto) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE yapper_database.category SET name =? WHERE categoryid =?");
            statement.setString(1, dto.getName());
            statement.setInt(2, dto.getId());
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to update category");
        }
    }

    /**
     * Deletes a category from the database.
     *
     * @param id the ID of the category to be deleted
     * @throws SQLException if there is an error during the delete operation
     */
    @Override
    public void deleteCategory(int id) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.category WHERE categoryid =?");
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to delete category");
        }
    }

    /**
     * Retrieves a category by its ID from the database.
     *
     * @param id the ID of the category to retrieve
     * @return a {@link CategoryDTO} object representing the category, or {@code null} if not found
     * @throws SQLException if there is an error during the retrieval
     */
    @Override
    public CategoryDTO getCategory(int id) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            CategoryDTO category = null;

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.category WHERE categoryid =?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                category = new CategoryDTO(
                        resultSet.getString("name"),
                        resultSet.getInt("categoryid"),
                        resultSet.getInt("addedBy"));
            }
            return category;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to get category");
        }
    }

    /**
     * Retrieves a category by its name from the database.
     *
     * @param name the name of the category to retrieve
     * @return a {@link CategoryDTO} object representing the category, or {@code null} if not found
     * @throws SQLException if there is an error during the retrieval
     */
    @Override
    public CategoryDTO getCategoryByName(String name) throws SQLException {
        CategoryDTO category = null;
        
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.category WHERE name =?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                category = new CategoryDTO(
                        resultSet.getString("name"),
                        resultSet.getInt("categoryid"),
                        resultSet.getInt("addedBy"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to get category");
        }
        return category;
    }

    /**
     * Retrieves all categories from the database.
     *
     * @return an {@link ArrayList} of {@link CategoryDTO} objects representing all categories
     * @throws SQLException if there is an error during the retrieval
     */
    @Override
    public ArrayList<CategoryDTO> getAllCategories() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.category");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CategoryDTO> categories = new ArrayList<>();
            while (resultSet.next()) {
                categories.add(new CategoryDTO(
                        resultSet.getString("name"),
                        resultSet.getInt("categoryid"),
                        resultSet.getInt("addedBy")));
            }
            return categories;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to get categories");
        }
    }
}