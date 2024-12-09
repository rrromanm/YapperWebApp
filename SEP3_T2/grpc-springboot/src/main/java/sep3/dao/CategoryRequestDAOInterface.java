package sep3.dao;

import sep3.dto.categoryRequest.CategoryRequestDTO;
import sep3.dto.categoryRequest.CreateCategoryRequestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryRequestDAOInterface
{
  void createCategoryRequest(CreateCategoryRequestDTO dto) throws SQLException;
  void deleteCategoryRequest(int id) throws SQLException;
  CategoryRequestDTO getCategoryRequest(int id) throws SQLException;
  ArrayList<CategoryRequestDTO> getAllCategoryRequests() throws SQLException;
  ArrayList<CategoryRequestDTO> getCategoryRequestsByName(String name) throws SQLException;
  void approveCategoryRequest(String categoryName, int addedBy) throws SQLException;
  void disapproveCategoryRequest(String categoryName) throws SQLException; // will delete all requests with the given category name
}
