package sep3.dto;

public class CreateCategoryDTO {
    private String name;
    private int addedBy;

    public CreateCategoryDTO(String Name, int AddedBy) {
        this.name = Name;
        this.addedBy = AddedBy;
    }

    public String getName() {
        return name;
    }
    public int getAddedBy() {
        return addedBy;
    }
}
