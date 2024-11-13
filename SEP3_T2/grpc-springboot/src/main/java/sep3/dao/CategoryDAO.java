package sep3.dao;

import sep3.dto.category.CategoryDTO;
import sep3.dto.category.CreateCategoryDTO;
import sep3.dto.category.UpdateCategoryDTO;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class CategoryDAO implements CategoryDAOInterface {

    private static CategoryDAO instance;

    private CategoryDAO() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=yapper_schema", "postgres", "via");

    }

    public static CategoryDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }
    @Override
    public void createCategory(CreateCategoryDTO dto) throws SQLException {
        try{
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.category (name, addedBy) VALUES (?,?)");
            statement.setString(1, dto.getName());
            statement.setInt(2, dto.getAddedBy());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            throw new SQLException("Failed to create category");
        }

    }

    @Override
    public void updateCategory(UpdateCategoryDTO dto) throws SQLException {
        try{
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE yapper_database.category SET name =? WHERE categoryid =?");
            statement.setString(1, dto.getName());
            statement.setInt(2, dto.getId());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            throw new SQLException("Failed to update category");
        }
    }

    @Override
    public void deleteCategory(int id) throws SQLException {
        try{
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.category WHERE categoryid =?");
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            throw new SQLException("Failed to delete category");
        }
    }

    @Override
    public CategoryDTO getCategory(int id) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.category WHERE categoryid =?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new CategoryDTO(
                        resultSet.getString("name"),
                        resultSet.getInt("categoryid"),
                        resultSet.getInt("addedBy"));
            } else {
                throw new SQLException("Category not found with id: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get category");
        }
    }

    @Override
    public CategoryDTO getCategoryByName(String name) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.category WHERE name =?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new CategoryDTO(
                        resultSet.getString("name"),
                        resultSet.getInt("categoryid"),
                        resultSet.getInt("addedBy"));
            } else {
                throw new SQLException("Category not found with name: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to get category");
        }
    }

    @Override
    public ArrayList<CategoryDTO> getAllCategories() throws SQLException {
        try{
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.category");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CategoryDTO> categories = new ArrayList<>();
            while(resultSet.next()){
                categories.add(new CategoryDTO(
                        resultSet.getString("name"),
                        resultSet.getInt("categoryid"),
                        resultSet.getInt("addedBy")));
            }
            return categories;
        }catch (Exception e){
            e.printStackTrace();
            throw new SQLException("Failed to get categories");
        }
    }

}
