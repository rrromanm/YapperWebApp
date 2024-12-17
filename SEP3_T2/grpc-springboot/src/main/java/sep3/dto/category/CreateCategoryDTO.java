package sep3.dto.category;

/**
 * Data transfer object for creating a new category.
 */
public class CreateCategoryDTO {
    private String name;
    private int addedBy;

    /**
     * Constructs a new CreateCategoryDTO with the provided values.
     *
     * @param Name The name of the category.
     * @param AddedBy The ID of the user who is adding the category.
     */
    public CreateCategoryDTO(String Name, int AddedBy) {
        this.name = Name;
        this.addedBy = AddedBy;
    }

    /**
     * Retrieves the name of the category.
     *
     * @return The name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the ID of the user who is adding the category.
     *
     * @return The ID of the user who is adding the category.
     */
    public int getAddedBy() {
        return addedBy;
    }
}
