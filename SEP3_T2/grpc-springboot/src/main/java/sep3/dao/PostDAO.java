package sep3.dao;

import org.checkerframework.checker.units.qual.C;
import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.PostDTO;
import sep3.dto.post.UpdatePostDTO;
import sep3.util.YapDate;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class PostDAO implements PostDAOInterface {
    private static PostDAO instance;

    /**
     * Private constructor to prevent direct instantiation.
     * Use {@link #getInstance()} to get the singleton instance.
     */
    private PostDAO() {}

    /**
     * Returns the singleton instance of {@code PostDAO}.
     *
     * @return the singleton instance of {@code PostDAO}
     * @throws SQLException if an error occurs while retrieving the instance
     */
    public static PostDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new PostDAO();
        }
        return instance;
    }

    /**
     * Creates a new post in the database.
     *
     * @param createPostDTO the data transfer object containing the post details
     * @throws SQLException if an error occurs while inserting the post into the database
     */
    @Override
    public void createPost(CreatePostDTO createPostDTO) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO yapper_database.post (title, body, userId) VALUES (?, ?, ?) RETURNING postId"
            );
            statement.setString(1, createPostDTO.getTitle());
            statement.setString(2, createPostDTO.getContent());
            statement.setInt(3, createPostDTO.getUserId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int postId = resultSet.getInt("postId");

                PreparedStatement categoryStatement = connection.prepareStatement(
                        "INSERT INTO yapper_database.post_category (categoryId, postId) VALUES (?, ?)"
                );

                categoryStatement.setInt(1, createPostDTO.getCategoryId());
                categoryStatement.setInt(2, postId);
                categoryStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to create post");
        }
    }

    /**
     * Updates an existing post in the database.
     *
     * @param updatePostDTO the data transfer object containing the updated post details
     * @throws SQLException if an error occurs while updating the post in the database
     */
    @Override
    public void updatePost(UpdatePostDTO updatePostDTO) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE yapper_database.post SET title = ?, body = ? WHERE postid = ? RETURNING postId");
            statement.setString(1, updatePostDTO.getTitle());
            statement.setString(2, updatePostDTO.getContent());
            statement.setInt(3, updatePostDTO.getPostId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int postId = resultSet.getInt("postId");

                PreparedStatement categoryStatement = connection.prepareStatement(
                        "UPDATE yapper_database.post_category SET categoryId = ? WHERE postId = ?"
                );

                categoryStatement.setInt(1, updatePostDTO.getCategoryId());
                categoryStatement.setInt(2, postId);
                categoryStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to update post");
        }
    }

    /**
     * Deletes a post from the database.
     *
     * @param id the ID of the post to delete
     * @throws SQLException if an error occurs while deleting the post from the database
     */

    @Override
    public void deletePost(int id) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.post WHERE postid = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete post");
        }
    }

    /**
     * Retrieves a single post by its ID from the database.
     *
     * @param id the ID of the post to retrieve
     * @return a {@link PostDTO} object representing the post
     * @throws SQLException if an error occurs while retrieving the post from the database
     */
    @Override
    public PostDTO getPost(int id) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT p.*, pc.categoryId FROM yapper_database.post p LEFT JOIN yapper_database.post_category pc ON p.postId = pc.postId WHERE p.postId = ? ORDER BY p.postDate DESC;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                YapDate date = new YapDate(resultSet.getTimestamp("postDate"));

                return new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        date.toString(),
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
                );
            } else {
                throw new SQLException("Post not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve post");
        }
    }

    /**
     * Retrieves all posts from the database.
     *
     * @return a list of {@link PostDTO} objects representing all posts
     * @throws SQLException if an error occurs while retrieving the posts from the database
     */
    @Override
    public ArrayList<PostDTO> getAllPosts() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT p.*, pc.categoryId FROM yapper_database.post p LEFT JOIN yapper_database.post_category pc ON p.postId = pc.postId ORDER BY p.postDate DESC;");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next()) {
                YapDate date = new YapDate(resultSet.getTimestamp("postDate"));

                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        date.toString(),
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
                ));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve posts");
        }
    }

    /**
     * Retrieves all posts from users that the specified user is following.
     *
     * @param userId The ID of the user whose following posts are to be retrieved.
     * @return A list of {@link PostDTO} objects representing the following posts.
     * @throws SQLException if an error occurs while retrieving following posts.
     */
    @Override
    public ArrayList<PostDTO> getAllFollowingPosts(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT p.*, pc.categoryId FROM yapper_database.post p LEFT JOIN yapper_database.post_category pc ON p.postId = pc.postId JOIN yapper_database.follows f ON p.userId = f.followedId WHERE f.followerId = ? ORDER BY p.postDate DESC;");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next()) {
                YapDate date = new YapDate(resultSet.getTimestamp("postDate"));

                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        date.toString(),
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
                ));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve following posts");
        }
    }

    /**
     * Retrieves all posts by a specific user.
     *
     * @param userId The ID of the user whose posts are to be retrieved.
     * @return A list of {@link PostDTO} objects representing the user's posts.
     * @throws SQLException if an error occurs while retrieving posts by the user.
     */
    @Override
    public ArrayList<PostDTO> getAllPostsById(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT post.*, pc.categoryId FROM yapper_database.post LEFT JOIN yapper_database.post_category pc ON post.postId = pc.postId WHERE post.userid = ? ORDER BY post.postDate DESC");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next()) {
                YapDate date = new YapDate(resultSet.getTimestamp("postDate"));

                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        date.toString(),
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
                ));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve posts by id");
        }
    }

    /**
     * Retrieves all posts belonging to a specific category.
     *
     * @param categoryId The ID of the category whose posts are to be retrieved.
     * @return A list of {@link PostDTO} objects representing the posts in the category.
     * @throws SQLException if an error occurs while retrieving posts by category.
     */
    @Override
    public ArrayList<PostDTO> getAllPostsByCategory(int categoryId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT p.*, pc.categoryId FROM post p JOIN post_category pc ON p.postId = pc.postId WHERE pc.categoryId = ? ORDER BY p.postDate DESC;");
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next()) {
                YapDate date = new YapDate(resultSet.getTimestamp("postDate"));

                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        date.toString(),
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
                ));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve posts by category");
        }
    }

    /**
     * Retrieves all posts that user has like.
     *
     * @param userId The ID of the user who liked the post.
     * @return A list of {@link PostDTO} objects representing the liked posts.
     * @throws SQLException if an error occurs while retrieving liked posts.
     */
    @Override
    public ArrayList<PostDTO> getAllLikedPosts(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT p.*, pc.categoryId FROM liked_post lp JOIN post p ON lp.postId = p.postId LEFT JOIN post_category pc ON p.postId = pc.postId WHERE lp.userId = ? ORDER BY p.postDate DESC;");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next()) {
                YapDate date = new YapDate(resultSet.getTimestamp("postDate"));

                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        date.toString(),
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
                ));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve posts by category");
        }
    }

    /**
     * Likes a post.
     *
     * @param userId The ID of the user who likes the post.
     * @param postId The ID of the post to be liked.
     * @throws SQLException if an error occurs while liking the post.
     */
    @Override
    public void likePost(int userId, int postId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.liked_post(userid, postid) VALUES (?,?)");
            statement.setInt(1, userId);
            statement.setInt(2, postId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to like post");
        }
    }

    /**
     * Unlikes a post.
     *
     * @param userId The ID of the user who unlikes the post.
     * @param postId The ID of the post to be unliked.
     * @throws SQLException if an error occurs while unliking the post.
     */
    @Override
    public void unlikePost(int userId, int postId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.liked_post WHERE userid = ? AND postid = ?");
            statement.setInt(1, userId);
            statement.setInt(2, postId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to unlike post");
        }
    }

    /**
     * Retrieves posts based on search criteria (title and body).
     *
     * @param search The term to search for in the title and body of the posts.
     * @return A list of {@link PostDTO} objects that match the search criteria.
     * @throws SQLException if an error occurs while searching for posts.
     */
    @Override
    public ArrayList<PostDTO> getPostsBySearch(String search) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT p.*, pc.categoryId\n" +
                            "FROM yapper_database.post p\n" +
                            "LEFT JOIN yapper_database.post_category pc ON p.postId = pc.postId\n" +
                            "WHERE LOWER(p.title) LIKE LOWER(?)\n" +
                            "OR LOWER(p.body) LIKE LOWER(?)\n" +
                            "ORDER BY p.postDate DESC;"
            );
            statement.setString(1,"%" + search + "%");
            statement.setString(2, "%" + search + "%");

            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next()) {
                YapDate date = new YapDate(resultSet.getTimestamp("postDate"));

                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        date.toString(),
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
                ));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve posts by search");
        }
    }
}