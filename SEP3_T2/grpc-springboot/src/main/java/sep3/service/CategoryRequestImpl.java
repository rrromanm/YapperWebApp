package sep3.service;

import CategoryRequest.*;
import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import sep3.dao.CategoryRequestDAOInterface;
import sep3.dto.categoryRequest.CategoryRequestDTO;
import sep3.dto.categoryRequest.CreateCategoryRequestDTO;

import java.util.ArrayList;

public class CategoryRequestImpl extends CategoryRequestServiceGrpc.CategoryRequestServiceImplBase
{
  private CategoryRequestDAOInterface dao;
  private Gson gson;

  public CategoryRequestImpl(CategoryRequestDAOInterface dao)
  {
    this.dao = dao;
    this.gson = new Gson();
  }

  @Override
  public void createCategoryRequest(CreateCategoryRequestRequest request, StreamObserver<EmptyCategoryRequestResponse> responseObserver)
  {
    try
    {
      CreateCategoryRequestDTO dto = new CreateCategoryRequestDTO(request.getName(), request.getUserId());
      dao.createCategoryRequest(dto);

      responseObserver.onNext(EmptyCategoryRequestResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override
  public void deleteCategoryRequest(DeleteCategoryRequestRequest request, StreamObserver<EmptyCategoryRequestResponse> responseObserver)
  {
    try
    {
      dao.deleteCategoryRequest(request.getId());

      responseObserver.onNext(EmptyCategoryRequestResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override public void getCategoryRequest(GetCategoryRequestRequest request, StreamObserver<CategoryRequestResponse> responseObserver)
  {
    try{
      CategoryRequestDTO categoryRequest = dao.getCategoryRequest(request.getId());

      responseObserver.onNext(CategoryRequestResponse.newBuilder()
        .setDate(categoryRequest.getDate())
        .setCategoryName(categoryRequest.getCategoryName())
        .setUserId(categoryRequest.getUserId())
        .setId(categoryRequest.getRequestId())
        .build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      System.err.println("Error occurred: " + e.getMessage());
      e.printStackTrace();
      responseObserver.onError(e);
    }
  }

  @Override
  public void getAllCategoriesRequest(EmptyGetAllCategoriesRequestRequest request, StreamObserver<GetAllCategoriesRequestResponse> responseObserver)
  {
    try{
      ArrayList<CategoryRequestDTO> categoryRequests = dao.getAllCategoryRequests();
      String string = gson.toJson(categoryRequests);
      GetAllCategoriesRequestResponse response = GetAllCategoriesRequestResponse.newBuilder().setList(string).build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      System.err.println("Error occurred: " + e.getMessage());
      e.printStackTrace(); 
      responseObserver.onError(e);
    }
  }

  @Override
  public void getCateogryRequestByName(GetCategoryRequestByNameRequest request, StreamObserver<GetAllCategoriesRequestResponse> responseObserver)
  {
    try{
      ArrayList<CategoryRequestDTO> categoryRequests = dao.getCategoryRequestsByName(request.getCategoryName());
      String string = gson.toJson(categoryRequests);
      GetAllCategoriesRequestResponse response = GetAllCategoriesRequestResponse.newBuilder().setList(string).build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override
  public void approveCategoryRequest(ApproveCategoryRequestRequest request, StreamObserver<EmptyCategoryRequestResponse> responseObserver)
  {
    try
    {
      dao.approveCategoryRequest(request.getCategoryName(), request.getAddedBy());

      responseObserver.onNext(EmptyCategoryRequestResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }

  @Override
  public void disapproveCategoryRequest(DisapproveCategoryRequestRequest request, StreamObserver<EmptyCategoryRequestResponse> responseObserver)
  {
    try
    {
      dao.disapproveCategoryRequest(request.getCategoryName());

      responseObserver.onNext(EmptyCategoryRequestResponse.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      responseObserver.onError(e);
    }
  }
}
