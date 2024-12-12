using App.Logic;
using DTOs.User;

namespace Tests.Logic;

public class CategoryLogicTests
{
    private CategoryLogic _logic;

    [SetUp]
    public void Setup()
    {
        _logic = new CategoryLogic(new GRPCService());
    }

    [Test]
    public async Task CreatingCategoryShouldAddItToTheList()
    {
        var createCategoryDTO = new CreateCategoryDTO
        {
            Name = "TestCategory",
            addedBy = 1
        };

        Assert.DoesNotThrowAsync(() => _logic.CreateCategoryAsync(createCategoryDTO));

        var categories = await _logic.GetAllCategoriesAsync();
        Assert.IsTrue(categories.Any(c => c.Name == "TestCategory"), "The category was not added.");
    }

    [Test]
    public async Task UpdatingCategoryShouldChangeItsName()
    {
        var createCategoryDTO = new CreateCategoryDTO
        {
            Name = "CategoryToUpdate",
            addedBy = 1
        };
        await _logic.CreateCategoryAsync(createCategoryDTO);

        var categories = await _logic.GetAllCategoriesAsync();
        var category = categories.FirstOrDefault(c => c.Name == "CategoryToUpdate");
        Assert.IsNotNull(category, "Category to update does not exist.");

        var updateCategoryDTO = new UpdateCategoryDTO
        {
            id = category.Id,
            name = "UpdatedCategoryName"
        };

        Assert.DoesNotThrowAsync(() => _logic.UpdateCategoryAsync(updateCategoryDTO));

        categories = await _logic.GetAllCategoriesAsync();
        Assert.IsTrue(categories.Any(c => c.Name == "UpdatedCategoryName"), "The category name was not updated.");
    }

    [Test]
    public async Task DeletingCategoryShouldRemoveItFromTheList()
    {
        var createCategoryDTO = new CreateCategoryDTO
        {
            Name = "CategoryToDelete",
            addedBy = 1
        };
        await _logic.CreateCategoryAsync(createCategoryDTO);

        var categories = await _logic.GetAllCategoriesAsync();
        var category = categories.FirstOrDefault(c => c.Name == "CategoryToDelete");
        Assert.IsNotNull(category, "Category to delete does not exist.");

        Assert.DoesNotThrowAsync(() => _logic.DeleteCategoryAsync(category.Id));

        categories = await _logic.GetAllCategoriesAsync();
        Assert.IsFalse(categories.Any(c => c.Name == "CategoryToDelete"), "The category was not deleted.");
    }

    [Test]
    public async Task GetCategoryByIdShouldReturnCorrectCategory()
    {
        var createCategoryDTO = new CreateCategoryDTO
        {
            Name = "CategoryById",
            addedBy = 1
        };
        await _logic.CreateCategoryAsync(createCategoryDTO);

        var categories = await _logic.GetAllCategoriesAsync();
        var category = categories.FirstOrDefault(c => c.Name == "CategoryById");
        Assert.IsNotNull(category, "Category to retrieve by ID does not exist.");

        var retrievedCategory = await _logic.GetCategoryById(category.Id);
        Assert.AreEqual(category.Id, retrievedCategory.Id, "Retrieved category ID does not match.");
        Assert.AreEqual(category.Name, retrievedCategory.Name, "Retrieved category name does not match.");
    }

    [Test]
    public async Task GetCategoryByNameShouldReturnCorrectCategory()
    {
        var createCategoryDTO = new CreateCategoryDTO
        {
            Name = "CategoryByName",
            addedBy = 1
        };
        await _logic.CreateCategoryAsync(createCategoryDTO);

        var retrievedCategory = await _logic.GetCategoryByName("CategoryByName");
        Assert.IsNotNull(retrievedCategory, "Category not found by name.");
        Assert.AreEqual("CategoryByName", retrievedCategory.Name, "Retrieved category name does not match.");
    }
}
