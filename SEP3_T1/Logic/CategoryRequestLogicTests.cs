using App.Logic;
using DTOs.DTOs.CategoryRequest;
using DTOs.Models;
using NUnit.Framework;

namespace Logic;

public class CategoryRequestTests
{
    private CategoryRequestLogic categoryRequestLogic;
    private CategoryLogic categoryLogic;

    // all tests are made with assumption that database is clear of any category request and categories
    [SetUp]
    public void Setup()
    {
        var grpcService = new GRPCService();
        categoryRequestLogic = new CategoryRequestLogic(grpcService);
        categoryLogic = new CategoryLogic(grpcService);
    }

    [Test]
    public async Task creating_category_request_adds_it_to_database()
    {
        CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO("Test Category Request", 1);
        Assert.DoesNotThrow(() => categoryRequestLogic.CreateCategoryRequest(dto));
        CategoryRequest categoryRequest = await categoryRequestLogic.GetCategoryRequestById(1);
        Assert.That(categoryRequest.CategoryName, Is.EqualTo("Test Category Request"));
        Assert.That(categoryRequest.UserId, Is.EqualTo(1));
        Assert.That(categoryRequest.RequestId, Is.Not.EqualTo(0));
    }
    
    [Test]
    public async Task deleting_category_request_deletes_it_from_database()
    {
        CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO("Test Category Request", 1);
        await categoryRequestLogic.CreateCategoryRequest(dto);
        CategoryRequest categoryRequest = await categoryRequestLogic.GetCategoryRequestById(1);
        Assert.DoesNotThrow(() => categoryRequestLogic.DeleteCategoryRequest(categoryRequest.RequestId));
        Assert.Throws<KeyNotFoundException>(() => categoryRequestLogic.GetCategoryRequestById(categoryRequest.RequestId));
    }
    
    [Test]
    public async Task getting_category_request_by_id_returns_correct_category_request()
    {
        CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO("Test Category Request", 1);
        await categoryRequestLogic.CreateCategoryRequest(dto);
        CategoryRequest categoryRequest = await categoryRequestLogic.GetCategoryRequestById(1);
        Assert.That(categoryRequest.CategoryName, Is.EqualTo("Test Category Request"));
        Assert.That(categoryRequest.UserId, Is.EqualTo(1));
        Assert.That(categoryRequest.RequestId, Is.Not.EqualTo(0));
    }
    
    [Test]
    public async Task getting_all_category_requests_returns_all_category_requests()
    {
        CreateCategoryRequestDTO dto1 = new CreateCategoryRequestDTO("Test Category Request 1", 1);
        CreateCategoryRequestDTO dto2 = new CreateCategoryRequestDTO("Test Category Request 2", 2);
        await categoryRequestLogic.CreateCategoryRequest(dto1);
        await categoryRequestLogic.CreateCategoryRequest(dto2);
        List<CategoryRequest> categoryRequests = await categoryRequestLogic.GetAllCategoryRequests();
        Assert.That(categoryRequests.Count, Is.EqualTo(2));
        Assert.That(categoryRequests[0].CategoryName, Is.EqualTo("Test Category Request 1"));
        Assert.That(categoryRequests[0].UserId, Is.EqualTo(1));
        Assert.That(categoryRequests[0].RequestId, Is.Not.EqualTo(0));
        Assert.That(categoryRequests[1].CategoryName, Is.EqualTo("Test Category Request 2"));
        Assert.That(categoryRequests[1].UserId, Is.EqualTo(2));
        Assert.That(categoryRequests[1].RequestId, Is.Not.EqualTo(0));
    } 
    
    [Test]
    public async Task getting_category_request_by_name_returns_correct_category_requests()
    {
        CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO("Test Category Request", 1);
        await categoryRequestLogic.CreateCategoryRequest(dto);
        List<CategoryRequest> categoryRequests = await categoryRequestLogic.GetCategoryRequestsByName("Test Category Request");
        Assert.That(categoryRequests[0].CategoryName, Is.EqualTo("Test Category Request"));
        Assert.That(categoryRequests[0].UserId, Is.EqualTo(1));
        Assert.That(categoryRequests[0].RequestId, Is.Not.EqualTo(0));
    }
    
    [Test]
    public async Task approving_category_request_approves_it()
    {
        CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO("Test Category Request", 1);
        await categoryRequestLogic.CreateCategoryRequest(dto);
        CategoryRequest categoryRequest = await categoryRequestLogic.GetCategoryRequestById(1);
        Assert.DoesNotThrow(() => categoryRequestLogic.ApproveCategoryRequest(categoryRequest.CategoryName, categoryRequest.UserId));
        Assert.ThrowsAsync<KeyNotFoundException>(() => categoryRequestLogic.GetCategoryRequestById(categoryRequest.RequestId));
        Category category = await categoryLogic.GetCategoryByName("Test Category Request");
        Assert.That(category.Name, Is.EqualTo("Test Category Request"));
        Assert.That(category.AddedBy, Is.EqualTo(1));
        Assert.That(category.Id, Is.Not.EqualTo(0));
    }
    
    [Test]
    public async Task disapproving_category_request_disapproves_it()
    {
        CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO("Test Category Request", 1);
        await categoryRequestLogic.CreateCategoryRequest(dto);
        CategoryRequest categoryRequest = await categoryRequestLogic.GetCategoryRequestById(1);
        Assert.DoesNotThrow(() => categoryRequestLogic.DisapproveCategoryRequest(categoryRequest.CategoryName));
        Assert.ThrowsAsync<KeyNotFoundException>(() => categoryRequestLogic.GetCategoryRequestById(categoryRequest.RequestId));
    } 
    
}