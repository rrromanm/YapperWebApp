package sep3.dao;

import org.junit.jupiter.api.*;
import sep3.dto.categoryRequest.CategoryRequestDTO;
import sep3.dto.categoryRequest.CreateCategoryRequestDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRequestDAOTest {
    private CategoryRequestDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = CategoryRequestDAO.getInstance();

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM category_request");
            statement.executeUpdate();
        }
    }

    @Test
    void createCategoryRequestInsertsIntoDatabase() throws SQLException {
        CreateCategoryRequestDTO createDto = new CreateCategoryRequestDTO("TestCategory", 1);
        dao.createCategoryRequest(createDto);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM category_request WHERE categoryName = ?");
            statement.setString(1, "TestCategory");
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("TestCategory", resultSet.getString("categoryName"));
            assertEquals(1, resultSet.getInt("userId"));
        }
    }

    @Test
    void deleteCategoryRequestRemovesFromDatabase() throws SQLException {
        CreateCategoryRequestDTO createDto = new CreateCategoryRequestDTO("ToDelete", 1);
        dao.createCategoryRequest(createDto);

        CategoryRequestDTO createdRequest = dao.getCategoryRequestsByName("ToDelete").get(0);
        dao.deleteCategoryRequest(createdRequest.getRequestId());

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM category_request WHERE requestId = ?");
            statement.setInt(1, createdRequest.getRequestId());
            ResultSet resultSet = statement.executeQuery();

            assertFalse(resultSet.next());
        }
    }

    @Test
    void getCategoryRequestReturnsCorrectData() throws SQLException {
        CreateCategoryRequestDTO createDto = new CreateCategoryRequestDTO("TestGet", 1);
        dao.createCategoryRequest(createDto);

        CategoryRequestDTO categoryRequest = dao.getCategoryRequestsByName("TestGet").get(0);
        CategoryRequestDTO retrievedRequest = dao.getCategoryRequest(categoryRequest.getRequestId());

        assertEquals(categoryRequest.getCategoryName(), retrievedRequest.getCategoryName());
        assertEquals(categoryRequest.getUserId(), retrievedRequest.getUserId());
        assertEquals(categoryRequest.getRequestId(), retrievedRequest.getRequestId());
    }

    @Test
    void getAllCategoryRequestsReturnsAllRequests() throws SQLException {
        dao.createCategoryRequest(new CreateCategoryRequestDTO("Request1", 1));
        dao.createCategoryRequest(new CreateCategoryRequestDTO("Request2", 2));

        ArrayList<CategoryRequestDTO> allRequests = dao.getAllCategoryRequests();
        assertEquals(2, allRequests.size());
    }

    @Test
    void getCategoryRequestsByNameReturnsFilteredResults() throws SQLException {
        dao.createCategoryRequest(new CreateCategoryRequestDTO("SpecificName", 1));
        dao.createCategoryRequest(new CreateCategoryRequestDTO("OtherName", 2));

        ArrayList<CategoryRequestDTO> filteredRequests = dao.getCategoryRequestsByName("Specific");
        assertEquals(1, filteredRequests.size());
        assertEquals("SpecificName", filteredRequests.get(0).getCategoryName());
    }

    @Test
    void approveCategoryRequestAddsToCategoryAndRemovesRequest() throws SQLException {
        dao.createCategoryRequest(new CreateCategoryRequestDTO("ApprovedCategory", 1));
        dao.approveCategoryRequest("ApprovedCategory", 2);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM category WHERE name = ?");
            statement.setString(1, "ApprovedCategory");
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals("ApprovedCategory", resultSet.getString("name"));
            assertEquals(2, resultSet.getInt("addedBy"));
        }

        ArrayList<CategoryRequestDTO> remainingRequests = dao.getCategoryRequestsByName("ApprovedCategory");
        assertTrue(remainingRequests.isEmpty());
    }

    @Test
    void disapproveCategoryRequestRemovesRequest() throws SQLException {
        dao.createCategoryRequest(new CreateCategoryRequestDTO("DisapprovedCategory", 1));
        dao.disapproveCategoryRequest("DisapprovedCategory");

        ArrayList<CategoryRequestDTO> remainingRequests = dao.getCategoryRequestsByName("DisapprovedCategory");
        assertTrue(remainingRequests.isEmpty());
    }
}
