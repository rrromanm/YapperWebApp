package sep3.dto.categoryRequest;

/**
 * Data transfer object representing a request to create a new category.
 */
public class CreateCategoryRequestDTO {
  private String categoryName;
  private int userId;

  /**
   * Constructs a new CreateCategoryRequestDTO with the provided category name and user ID.
   *
   * @param CategoryName The name of the category being requested.
   * @param UserId The ID of the user making the request.
   */
  public CreateCategoryRequestDTO(String CategoryName, int UserId) {
    this.categoryName = CategoryName;
    this.userId = UserId;
  }

  /**
   * Retrieves the ID of the user who made the category creation request.
   *
   * @return The ID of the user.
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Retrieves the name of the requested category.
   *
   * @return The name of the category.
   */
  public String getCategoryName() {
    return categoryName;
  }
}
