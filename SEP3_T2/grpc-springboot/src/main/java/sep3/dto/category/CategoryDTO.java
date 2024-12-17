package sep3.dto.category;

/**
 * Data transfer object representing a category.
 */
public class CategoryDTO {
    private String name;
    private int id;
    private int addedBy;

    /**
     * Constructs a new CategoryDTO with the provided values.
     *
     * @param Name The name of the category.
     * @param Id The ID of the category.
     * @param AddedBy The ID of the user who added the category.
     */
    public CategoryDTO(String Name, int Id, int AddedBy) {
        this.name = Name;
        this.id = Id;
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
     * Retrieves the ID of the category.
     *
     * @return The ID of the category.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the ID of the user who added the category.
     *
     * @return The ID of the user who added the category.
     */
    public int getAddedBy() {
        return addedBy;
    }
}
