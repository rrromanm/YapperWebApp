package sep3.dto;

public class CategoryDTO {
    private String name;
    private int id;

    private int addedBy;

    public CategoryDTO(String Name, int Id, int AddedBy) {
        this.name = Name;
        this.id = Id;
        this.addedBy = AddedBy;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAddedBy() {
        return addedBy;
    }
}
