package sep3.service;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import postCategory.*;
import sep3.dao.CategoryDAOInterface;
import sep3.dto.category.CategoryDTO;
import sep3.dto.category.CreateCategoryDTO;
import sep3.dto.category.UpdateCategoryDTO;

import java.util.ArrayList;

public class CategoryImpl extends CategoryServiceGrpc.CategoryServiceImplBase {

    private CategoryDAOInterface dao;
    private final Gson gson;

    public CategoryImpl(CategoryDAOInterface dao) {
        this.dao = dao;
        this.gson = new Gson();
    }

    /**
     * Creates a new category.
     *
     * @param request The request containing the category details to create.
     * @param responseObserver The stream observer to send responses.
     */
    @Override
    public void createCategory(CreateCategoryRequest request, StreamObserver<EmptyCategoryResponse> responseObserver) {
        try{
            CreateCategoryDTO dto = new CreateCategoryDTO(request.getName(), request.getAddedBy());
            dao.createCategory(dto);
            responseObserver.onNext(EmptyCategoryResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }

    }

    /**
     * Updates an existing category.
     *
     * @param request The request containing the category details to update.
     * @param responseObserver The stream observer to send responses.
     */
    @Override
    public void updateCategory(UpdateCategoryRequest request, StreamObserver<EmptyCategoryResponse> responseObserver) {
        try{

            UpdateCategoryDTO dto = new UpdateCategoryDTO(request.getName(), request.getId());

            dao.updateCategory(dto);
            EmptyCategoryResponse response = EmptyCategoryResponse.newBuilder().build();

            responseObserver.onNext(EmptyCategoryResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }
    }

    /**
     * Deletes a category by its ID.
     *
     * @param request The request containing the category ID to delete.
     * @param responseObserver The stream observer to send responses.
     */
    @Override
    public void deleteCategory(DeleteCategoryRequest request, StreamObserver<EmptyCategoryResponse> responseObserver) {
        try{
            int id = request.getId();

            dao.deleteCategory(id);

            responseObserver.onNext(EmptyCategoryResponse.newBuilder().build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param request The request containing the category ID to retrieve.
     * @param responseObserver The stream observer to send the category details.
     */
    @Override
    public void getCategory(GetCategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        try{

            CategoryDTO category = dao.getCategory(request.getId());

            responseObserver.onNext(CategoryResponse.newBuilder().setId(category.getId()).setName(category.getName()).setAddedBy(category.getAddedBy()).build());
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(e);
        }
    }

    /**
     * Retrieves a category by its name.
     *
     * @param request The request containing the category name to retrieve.
     * @param responseObserver The stream observer to send the category details.
     */
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

    /**
     * Retrieves all categories.
     *
     * @param request The request to retrieve all categories.
     * @param responseObserver The stream observer to send the list of categories.
     */
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