using NUnit.Framework;

namespace Logic;
using App.Logic;
using DTOs.Models;
using DTOs.User;
    public class CategoryLogicTests
    {
        private CategoryLogic categoryLogic;
    
        [SetUp]
        public void Setup()
        {
            categoryLogic = new CategoryLogic(new GRPCService());
        }
    
        [Test]
        public async Task creating_category_adds_it_to_database()
        {
            CreateCategoryDTO dto = new CreateCategoryDTO();
            dto.Name = "Test";
            dto.addedBy = 1;
            Assert.DoesNotThrowAsync(() => categoryLogic.CreateCategoryAsync(dto));
            Category category = await categoryLogic.GetCategoryByName("Test");
            Assert.That(category.Name, Is.EqualTo("Test"));
            Assert.That(category.AddedBy, Is.EqualTo(1));
            Assert.That(category.Id, Is.Not.EqualTo(0));
        }

        [Test]
        public async Task updating_category_updates_it_in_database()
        {
            CreateCategoryDTO dto = new CreateCategoryDTO();
            dto.Name = "Test";
            dto.addedBy = 1;
            await categoryLogic.CreateCategoryAsync(dto);
            Category category = await categoryLogic.GetCategoryByName("Test");
            UpdateCategoryDTO updateDto = new UpdateCategoryDTO();
            updateDto.id = category.Id;
            updateDto.name = "Test2";
            Assert.DoesNotThrowAsync(() => categoryLogic.UpdateCategoryAsync(updateDto));
            Category updatedCategory = await categoryLogic.GetCategoryByName("Test2");
            Assert.That(updatedCategory.Name, Is.EqualTo("Test2"));
            Assert.That(updatedCategory.AddedBy, Is.EqualTo(1));
            Assert.That(updatedCategory.Id, Is.EqualTo(category.Id));
        }
        
        [Test]
        public async Task deleting_category_deletes_it_from_database()
        {
            CreateCategoryDTO dto = new CreateCategoryDTO();
            dto.Name = "Test";
            dto.addedBy = 1;
            await categoryLogic.CreateCategoryAsync(dto);
            Category category = await categoryLogic.GetCategoryByName("Test");
            Assert.DoesNotThrowAsync(() => categoryLogic.DeleteCategoryAsync(category.Id));
            Assert.ThrowsAsync<Exception>(() => categoryLogic.GetCategoryByName("Test"));
        }
        
        [Test]
        public async Task getting_category_by_name_returns_correct_category()
        {
            CreateCategoryDTO dto = new CreateCategoryDTO();
            dto.Name = "Test";
            dto.addedBy = 1;
            await categoryLogic.CreateCategoryAsync(dto);
            Category category = await categoryLogic.GetCategoryByName("Test");
            Assert.That(category.Name, Is.EqualTo("Test"));
            Assert.That(category.AddedBy, Is.EqualTo(1));
            Assert.That(category.Id, Is.Not.EqualTo(0));
        }
        
        [Test]
        public async Task getting_category_by_id_returns_correct_category()
        {
            CreateCategoryDTO dto = new CreateCategoryDTO();
            dto.Name = "Test";
            dto.addedBy = 1;
            await categoryLogic.CreateCategoryAsync(dto);
            Category category = await categoryLogic.GetCategoryByName("Test");
            Category categoryById = await categoryLogic.GetCategoryById(category.Id);
            Assert.That(categoryById.Name, Is.EqualTo("Test"));
            Assert.That(categoryById.AddedBy, Is.EqualTo(1));
            Assert.That(categoryById.Id, Is.EqualTo(category.Id));
        }
        
        [Test]
        public async Task getting_all_categories_returns_all_categories()
        {
            CreateCategoryDTO dto = new CreateCategoryDTO();
            dto.Name = "Test";
            dto.addedBy = 1;
            await categoryLogic.CreateCategoryAsync(dto);
            CreateCategoryDTO dto2 = new CreateCategoryDTO();
            dto2.Name = "Test2";
            dto2.addedBy = 1;
            await categoryLogic.CreateCategoryAsync(dto2);
            List<Category> categories = await categoryLogic.GetAllCategoriesAsync();
            Assert.That(categories.Count, Is.EqualTo(2));
            Assert.That(categories[0].Name, Is.EqualTo("Test"));
            Assert.That(categories[1].Name, Is.EqualTo("Test2"));
        }
        

    }