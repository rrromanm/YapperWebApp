package sep3.dao;

import org.junit.jupiter.api.*;
import sep3.dto.post.PostDTO;
import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.UpdatePostDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PostDAOTest {
    private PostDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = PostDAO.getInstance();

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM liked_post");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM post_category");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM post");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM category WHERE categoryId IN (1, 2)");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM social_media_user WHERE userId IN (100, 101)");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO social_media_user (userId, nickname, username, email, password) VALUES (100, 'user1', 'user1', 'user1@example.com', 'password1')");
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO social_media_user (userId, nickname, username, email, password) VALUES (101, 'user2', 'user2', 'user2@example.com', 'password2')");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO category (categoryId, name) VALUES (1, 'Category 1')");
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO category (categoryId, name) VALUES (2, 'Category 2')");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO post (postId, title, body, userid) VALUES (1000, 'Post 1', 'Content 1', 100)");
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO post (postId, title, body, userid) VALUES (1001, 'Post 2', 'Content 2', 101)");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO post_category (postId, categoryId) VALUES (1000, 1)");
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO post_category (postId, categoryId) VALUES (1001, 2)");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement postDeleteStatement = connection.prepareStatement("DELETE FROM post");
            postDeleteStatement.executeUpdate();

            PreparedStatement categoryDeleteStatement = connection.prepareStatement("DELETE FROM category WHERE categoryId IN (1, 2)");
            categoryDeleteStatement.executeUpdate();

            PreparedStatement userDeleteStatement = connection.prepareStatement("DELETE FROM social_media_user WHERE userId IN (100, 101)");
            userDeleteStatement.executeUpdate();
        }
    }

    @Test
    void createPostInsertsIntoDatabase() throws SQLException {
        CreatePostDTO createPostDto = new CreatePostDTO("New Post", "Content for new post", 1, 100);
        dao.createPost(createPostDto);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM post WHERE title = ?");
            statement.setString(1, "New Post");
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("New Post", resultSet.getString("title"));
            assertEquals("Content for new post", resultSet.getString("body"));
            assertEquals(100, resultSet.getInt("userid"));
        }
    }

    @Test
    void updatePostUpdatesDatabase() throws SQLException {
        CreatePostDTO createPostDto = new CreatePostDTO("Initial Post", "Content for initial post", 1, 100);  // userId and categoryId
        dao.createPost(createPostDto);

        PostDTO createdPost = dao.getAllPosts().get(0);

        UpdatePostDTO updatePostDto = new UpdatePostDTO("Updated Post", "Updated content", 1,createdPost.getPostId());
        dao.updatePost(updatePostDto);

        PostDTO updatedPost = dao.getPost(createdPost.getPostId());
        assertEquals("Updated Post", updatedPost.getTitle());
        assertEquals("Updated content", updatedPost.getBody());
    }

    @Test
    void deletePostRemovesFromDatabase() throws SQLException {
        CreatePostDTO createPostDto = new CreatePostDTO("Post to Delete", "Content for post to delete", 1, 100);  // userId and categoryId
        dao.createPost(createPostDto);

        PostDTO createdPost = dao.getAllPosts().get(0);
        dao.deletePost(createdPost.getPostId());

        assertThrows(SQLException.class, () -> dao.getPost(createdPost.getPostId()));
    }

    @Test
    void getAllPostsReturnsAllPosts() throws SQLException {
        ArrayList<PostDTO> allPosts = dao.getAllPosts();
        assertEquals(2, allPosts.size());
    }

    @Test
    void getPostReturnsCorrectPost() throws SQLException {
        dao.createPost(new CreatePostDTO("Post 1", "Content for Post 1", 1, 100));  // userId and categoryId

        PostDTO createdPost = dao.getAllPosts().get(0);
        PostDTO retrievedPost = dao.getPost(createdPost.getPostId());

        assertEquals(createdPost.getPostId(), retrievedPost.getPostId());
        assertEquals(createdPost.getTitle(), retrievedPost.getTitle());
        assertEquals(createdPost.getBody(), retrievedPost.getBody());
    }

    @Test
    void getPostsByUserIdReturnsFilteredPosts() throws SQLException {
        ArrayList<PostDTO> postsByUserId = dao.getAllPostsById(100);
        assertEquals(1, postsByUserId.size());
        assertEquals("Post 1", postsByUserId.get(0).getTitle());
    }

    @Test
    void getAllPostsByCategoryReturnsFilteredPosts() throws SQLException {
        ArrayList<PostDTO> posts = dao.getAllPostsByCategory(1);
        assertEquals(1, posts.size());
        assertEquals("Post 1", posts.get(0).getTitle());
        assertEquals(100, posts.get(0).getUserId());
    }

    @Test
    void getAllLikedPostsReturnsLikedPosts() throws SQLException {
        dao.likePost(100, 1000);
        dao.likePost(101, 1001);

        ArrayList<PostDTO> likedPosts = dao.getAllLikedPosts(100);
        assertEquals(1, likedPosts.size());
        assertEquals("Post 1", likedPosts.get(0).getTitle());
        assertEquals(1000, likedPosts.get(0).getPostId());
    }

    @Test
    void likePostInsertsLike() throws SQLException {
        dao.likePost(100, 1000);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM liked_post WHERE userId = ? AND postId = ?");
            statement.setInt(1, 100);
            statement.setInt(2, 1000);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
        }
    }

    @Test
    void unlikePostRemovesLike() throws SQLException {
        dao.likePost(100, 1000);
        dao.unlikePost(100, 1000);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM liked_post WHERE userId = ? AND postId = ?");
            statement.setInt(1, 100);
            statement.setInt(2, 1000);
            ResultSet resultSet = statement.executeQuery();

            assertFalse(resultSet.next());
        }
    }

    @Test
    void getPostsBySearchReturnsMatchingPosts() throws SQLException {
        ArrayList<PostDTO> posts = dao.getPostsBySearch("Post");
        assertEquals(2, posts.size());

        posts = dao.getPostsBySearch("Content 1");
        assertEquals(1, posts.size());
        assertEquals("Post 1", posts.get(0).getTitle());
    }

    @Test
    
}
