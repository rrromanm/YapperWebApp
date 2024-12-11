package sep3.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep3.dto.category.CategoryDTO;
import sep3.dto.category.CreateCategoryDTO;
import sep3.dto.category.UpdateCategoryDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOTest {

    private CategoryDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = CategoryDAO.getInstance();

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.category");
            statement.executeUpdate();
        }
    }

    @Test
    void creatingCategoryAddsItToTheDatabase() throws SQLException {
        CreateCategoryDTO dto = new CreateCategoryDTO("TestCategory", 1);
        dao.createCategory(dto);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.category WHERE name = ?");
            statement.setString(1, "TestCategory");
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("TestCategory", resultSet.getString("name"));
            assertEquals(1, resultSet.getInt("addedBy"));
        }
    }

    @Test
    void creatingCategoryWithDuplicateNameThrowsException() {
        CreateCategoryDTO dto = new CreateCategoryDTO("DuplicateCategory", 1);
        assertDoesNotThrow(() -> dao.createCategory(dto));

        SQLException exception = assertThrows(SQLException.class, () -> dao.createCategory(dto));
        assertTrue(exception.getMessage().contains("Failed to create category"));
    }

    @Test
    void updatingCategoryUpdatesTheDatabase() throws SQLException {
        CreateCategoryDTO createDto = new CreateCategoryDTO("OldName", 1);
        dao.createCategory(createDto);

        CategoryDTO createdCategory = dao.getCategoryByName("OldName");
        assertNotNull(createdCategory);

        dao.updateCategory(new UpdateCategoryDTO("NewName", createdCategory.getId()));

        CategoryDTO updatedCategory = dao.getCategory(createdCategory.getId());
        assertEquals("NewName", updatedCategory.getName());
    }

    @Test
    void gettingAllCategoriesReturnsCorrectData() throws SQLException {
        CreateCategoryDTO dto1 = new CreateCategoryDTO("Category1", 1);
        CreateCategoryDTO dto2 = new CreateCategoryDTO("Category2", 2);

        dao.createCategory(dto1);
        dao.createCategory(dto2);

        ArrayList<CategoryDTO> categories = dao.getAllCategories();
        assertEquals(2, categories.size());
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Category1")));
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Category2")));
    }

    @Test
    void gettingCategoryByNameReturnsCorrectData() throws SQLException {
        CreateCategoryDTO dto = new CreateCategoryDTO("UniqueCategory", 1);
        dao.createCategory(dto);

        CategoryDTO category = dao.getCategoryByName("UniqueCategory");
        assertNotNull(category);
        assertEquals("UniqueCategory", category.getName());
        assertEquals(1, category.getAddedBy());
    }

    @Test
    void gettingCategoryByIdReturnsCorrectData() throws SQLException {
        CreateCategoryDTO dto = new CreateCategoryDTO("ByIdCategory", 1);
        dao.createCategory(dto);

        CategoryDTO category = dao.getCategoryByName("ByIdCategory");
        assertNotNull(category);

        CategoryDTO fetchedCategory = dao.getCategory(category.getId());
        assertEquals(category.getId(), fetchedCategory.getId());
        assertEquals(category.getName(), fetchedCategory.getName());
    }
}
