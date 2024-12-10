package sep3.dto.categoryRequest;

public class CreateCategoryRequestDTO
{
  private String categoryName;
  private int userId;

  public CreateCategoryRequestDTO(String CategoryName, int UserId)
  {
    this.categoryName = CategoryName;
    this.userId = UserId;
  }
  public int getUserId()
  {
    return userId;
  }
  public String getCategoryName()
  {
    return categoryName;
  }
}
