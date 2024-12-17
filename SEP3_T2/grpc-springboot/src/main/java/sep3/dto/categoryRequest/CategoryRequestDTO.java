package sep3.dto.categoryRequest;

/**
 * Data transfer object representing a category request.
 */
public class CategoryRequestDTO {
  private String date;
  private String categoryName;
  private int userId;
  private int requestId;

  /**
   * Constructs a new CategoryRequestDTO with the provided values.
   *
   * @param Date The date when the category request was made.
   * @param CategoryName The name of the requested category.
   * @param UserId The ID of the user making the request.
   * @param RequestId The unique ID of the category request.
   */
  public CategoryRequestDTO(String Date, String CategoryName, int UserId, int RequestId) {
    this.date = Date;
    this.categoryName = CategoryName;
    this.userId = UserId;
    this.requestId = RequestId;
  }

  /**
   * Retrieves the unique ID of the category request.
   *
   * @return The ID of the category request.
   */
  public int getRequestId() {
    return requestId;
  }

  /**
   * Retrieves the date when the category request was made.
   *
   * @return The date of the request.
   */
  public String getDate() {
    return date;
  }

  /**
   * Retrieves the name of the requested category.
   *
   * @return The name of the category.
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * Retrieves the ID of the user who made the category request.
   *
   * @return The ID of the user.
   */
  public int getUserId() {
    return userId;
  }
}
