package sep3.dao;

import sep3.dto.category.CategoryDTO;
import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.PostDTO;
import sep3.dto.post.UpdatePostDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PostDAOInterface {
    void createPost(CreatePostDTO createPostDTO) throws SQLException;
    void updatePost(UpdatePostDTO updatePostDTO) throws SQLException;
    void deletePost(int id) throws SQLException;
    PostDTO getPost(int id) throws SQLException;
    ArrayList<PostDTO> getAllPosts() throws SQLException;
    ArrayList<PostDTO> getAllFollowingPosts(int userId) throws SQLException;
    ArrayList<PostDTO> getAllPostsById(int userId) throws SQLException;
    ArrayList<PostDTO> getAllPostsByCategory(int categoryId) throws SQLException;
    void likePost(int userId, int postId) throws SQLException;
    void unlikePost(int userId, int postId) throws SQLException;
}
