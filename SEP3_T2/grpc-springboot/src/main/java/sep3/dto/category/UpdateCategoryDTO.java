package sep3.dto.category;

/**
 * Data transfer object for updating an existing category.
 */
public class UpdateCategoryDTO {
    private String name;
    private int id;

    /**
     * Constructs a new UpdateCategoryDTO with the provided values.
     *
     * @param Name The name of the category.
     * @param Id The ID of the category to be updated.
     */
    public UpdateCategoryDTO(String Name, int Id) {
        this.name = Name;
        this.id = Id;
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
     * Retrieves the ID of the category to be updated.
     *
     * @return The ID of the category.
     */
    public int getId() {
        return id;
    }
}
