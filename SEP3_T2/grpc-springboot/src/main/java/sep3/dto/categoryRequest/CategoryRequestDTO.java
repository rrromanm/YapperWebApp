package sep3.dto.categoryRequest;

public class CategoryRequestDTO
{
  private String date;
  private String categoryName;
  private int userId;
  private int requestId;

  public CategoryRequestDTO(String Date, String CategoryName, int UserId, int RequestId)
  {
    this.date = Date;
    this.categoryName = CategoryName;
    this.userId = UserId;
    this.requestId = RequestId;
  }

  public int getRequestId()
  {
    return requestId;
  }
  public String getDate()
  {
    return date;
  }
  public String getCategoryName()
  {
    return categoryName;
  }

  public int getUserId()
  {
    return userId;
  }
}
