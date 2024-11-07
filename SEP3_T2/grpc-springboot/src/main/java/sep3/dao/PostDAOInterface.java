package sep3.dao;

import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.UpdatePostDTO;

import java.sql.SQLException;

public interface PostDAOInterface {
    void createPost(CreatePostDTO createPostDTO) throws SQLException;
    void updatePost(UpdatePostDTO updatePostDTO) throws SQLException;
    void deletePost(int id) throws SQLException;
}
