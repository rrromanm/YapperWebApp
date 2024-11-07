package sep3.dto.category;

public class UpdateCategoryDTO {
    private String name;
    private int id;

    public UpdateCategoryDTO(String Name, int Id) {
        this.name = Name;
        this.id = Id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
