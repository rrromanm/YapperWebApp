package sep3.dao;

import sep3.dto.CommentDTO;
import sep3.dto.CreateCommentDTO;
import sep3.dto.UpdateCommentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CommentDAOInterface
{
    void createComment(CreateCommentDTO dto) throws SQLException;
    void updateComment(UpdateCommentDTO dto) throws SQLException;
    void deleteComment(int commentId) throws SQLException;
    CommentDTO getComment(int commentId) throws SQLException;
    ArrayList<CommentDTO> getAllComments() throws SQLException;
    ArrayList<CommentDTO> getCommentsByPostId(int postId) throws SQLException; // including the commentDate recent to latest or based on likeCount
    ArrayList<CommentDTO> getCommentsByUserId(int userId) throws SQLException; // including the commentDate recent to latest or based on likeCount

}