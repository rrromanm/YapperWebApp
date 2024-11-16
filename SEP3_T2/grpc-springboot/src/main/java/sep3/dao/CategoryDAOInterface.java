package sep3.dao;

import sep3.dto.category.CategoryDTO;
import sep3.dto.category.CreateCategoryDTO;
import sep3.dto.category.UpdateCategoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryDAOInterface {
    void createCategory(CreateCategoryDTO dto) throws SQLException;
    void updateCategory(UpdateCategoryDTO dto) throws SQLException;
    void deleteCategory(int id) throws SQLException;
    CategoryDTO getCategory(int id) throws SQLException;
    ArrayList<CategoryDTO> getAllCategories() throws SQLException;
    CategoryDTO getCategoryByName(String name) throws SQLException;
}
