package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import postCategory.*;
import sep3.dao.CategoryDAO;
import sep3.dao.CategoryDAOInterface;
import sep3.dto.CategoryDTO;
import sep3.dto.CreateCategoryDTO;
import sep3.dto.UpdateCategoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryImpl extends CategoryServiceGrpc.CategoryServiceImplBase {

    private CategoryDAOInterface dao;
    private Gson gson;

    public CategoryImpl(CategoryDAOInterface dao) {
        this.dao = dao;
        this.gson = new Gson();
    }
    @Override
    public void createCategory(CreateCategoryRequest request, StreamObserver<EmptyCategoryResponse> responseObserver) {
        try{
            System.out.println("Category created with name: " + request.getName());
            CreateCategoryDTO dto = new CreateCategoryDTO(request.getName(), request.getAddedBy());
            dao.createCategory(dto);
            responseObserver.onNext(EmptyCategoryResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }

    }

    @Override
    public void updateCategory(UpdateCategoryRequest request, StreamObserver<EmptyCategoryResponse> responseObserver) {
        try{
            System.out.println("Category updated with id: " + request.getId());

            UpdateCategoryDTO dto = new UpdateCategoryDTO(request.getName(), request.getId());
            dao.updateCategory(dto);
            EmptyCategoryResponse response = EmptyCategoryResponse.newBuilder().build();
            responseObserver.onNext(EmptyCategoryResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteCategory(DeleteCategoryRequest request, StreamObserver<EmptyCategoryResponse> responseObserver) {
        try{
            int id = request.getId();
            System.out.println("Category deleted with id: " + id);

            dao.deleteCategory(id);

            responseObserver.onNext(EmptyCategoryResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCategory(GetCategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        try{
            System.out.println("Category retrieved with id: " + request.getId());

            CategoryDTO category = dao.getCategory(request.getId());
            responseObserver.onNext(CategoryResponse.newBuilder().setId(category.getId()).setName(category.getName()).setAddedBy(category.getAddedBy()).build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCategoryByName(GetCategoryByNameRequest request, StreamObserver<CategoryResponse> responseObserver) {
        try{
            CategoryDTO category = dao.getCategoryByName(request.getName());
            responseObserver.onNext(CategoryResponse.newBuilder().setId(category.getId()).setName(category.getName()).setAddedBy(category.getAddedBy()).build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllCategories(EmptyGetAllCategoriesRequest request, StreamObserver<GetAllCategoriesResponse> responseObserver) {
        try{
            ArrayList<CategoryDTO> categories = dao.getAllCategories();
            String string = gson.toJson(categories);
            GetAllCategoriesResponse response = GetAllCategoriesResponse.newBuilder().setList(string).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }
    }
}