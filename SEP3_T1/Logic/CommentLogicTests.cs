using App.Logic;
using DTOs.DTOs.Comment;
using DTOs.Models;
using NUnit.Framework;

namespace Logic;

public class CommentLogicTests
{
    private CommentLogic commentLogic;
    
    [SetUp]
    public void Setup()
    {
        commentLogic = new CommentLogic(new GRPCService());
    }
    
    [Test]
    public async Task creating_comment_adds_it_to_database()
    {
        CreateCommentDTO dto = new CreateCommentDTO("Test Comment", 1, 1);
        Assert.DoesNotThrowAsync(() => commentLogic.CreateCommentAsync(dto));
        Comment comment = await commentLogic.GetCommentAsync(1);
        Assert.That(comment.body, Is.EqualTo("Test Comment"));
        Assert.That(comment.commentDate, Is.Not.EqualTo(null));
        Assert.That(comment.likeCount, Is.EqualTo(0));
        Assert.That(comment.commentId, Is.Not.EqualTo(0));
        Assert.That(comment.userId, Is.EqualTo(1));
        Assert.That(comment.postId, Is.EqualTo(1));
    }
    
    [Test]
    public async Task updating_comment_updates_it_in_database()
    {
        CreateCommentDTO dto = new CreateCommentDTO("Test Comment", 1, 1);
        await commentLogic.CreateCommentAsync(dto);
        Comment comment = await commentLogic.GetCommentAsync(1);
        UpdateCommentDTO updateDto = new UpdateCommentDTO(comment.commentId, "Updated Test Comment");
        Assert.DoesNotThrowAsync(() => commentLogic.UpdateCommentAsync(updateDto));
        Comment updatedComment = await commentLogic.GetCommentAsync(comment.commentId);
        Assert.That(updatedComment.body, Is.EqualTo("Updated Test Comment"));
        Assert.That(updatedComment.commentDate, Is.Not.EqualTo(null));
        Assert.That(updatedComment.likeCount, Is.EqualTo(0));
        Assert.That(updatedComment.commentId, Is.EqualTo(comment.commentId));
        Assert.That(updatedComment.userId, Is.EqualTo(1));
        Assert.That(updatedComment.postId, Is.EqualTo(1));
    }
    
    [Test]
    public async Task deleting_comment_deletes_it_from_database()
    {
        CreateCommentDTO dto = new CreateCommentDTO("Test Comment", 1, 1);
        await commentLogic.CreateCommentAsync(dto);
        Comment comment = await commentLogic.GetCommentAsync(1);
        Assert.DoesNotThrowAsync(() => commentLogic.DeleteCommentAsync(comment.commentId));
        Assert.ThrowsAsync<Exception>(() => commentLogic.GetCommentAsync(comment.commentId));
    }
    
    [Test]
    public async Task getting_comment_by_id_returns_correct_comment()
    {
        CreateCommentDTO dto = new CreateCommentDTO("Test Comment", 1, 1);
        await commentLogic.CreateCommentAsync(dto);
        Comment comment = await commentLogic.GetCommentAsync(1);
        Assert.That(comment.body, Is.EqualTo("Test Comment"));
        Assert.That(comment.commentDate, Is.Not.EqualTo(null));
        Assert.That(comment.likeCount, Is.EqualTo(0));
        Assert.That(comment.commentId, Is.Not.EqualTo(0));
        Assert.That(comment.userId, Is.EqualTo(1));
        Assert.That(comment.postId, Is.EqualTo(1));
    }
    
    [Test]
    public async Task getting_all_comments_returns_all_comments()
    {
        CreateCommentDTO dto1 = new CreateCommentDTO("Test Comment 1", 1, 1);
        CreateCommentDTO dto2 = new CreateCommentDTO("Test Comment 2", 2, 2);
        await commentLogic.CreateCommentAsync(dto1);
        await commentLogic.CreateCommentAsync(dto2);
        List<Comment> comments = await commentLogic.GetAllCommentsAsync();
        Assert.That(comments.Count, Is.EqualTo(2));
        Assert.That(comments[0].body, Is.EqualTo("Test Comment 1"));
        Assert.That(comments[0].commentDate, Is.Not.EqualTo(null));
        Assert.That(comments[0].likeCount, Is.EqualTo(0));
        Assert.That(comments[0].commentId, Is.Not.EqualTo(0));
        Assert.That(comments[0].userId, Is.EqualTo(1));
        Assert.That(comments[0].postId, Is.EqualTo(1));
        Assert.That(comments[1].body, Is.EqualTo("Test Comment 2"));
        Assert.That(comments[1].commentDate, Is.Not.EqualTo(null));
        Assert.That(comments[1].likeCount, Is.EqualTo(0));
        Assert.That(comments[1].commentId, Is.Not.EqualTo(0));
        Assert.That(comments[1].userId, Is.EqualTo(2));
        Assert.That(comments[1].postId, Is.EqualTo(2));
    }
    
    [Test]
    public async Task getting_comments_by_post_id_returns_correct_comments()
    {
        CreateCommentDTO dto1 = new CreateCommentDTO("Test Comment 1", 1, 1);
        CreateCommentDTO dto2 = new CreateCommentDTO("Test Comment 2", 1, 1);
        await commentLogic.CreateCommentAsync(dto1);
        await commentLogic.CreateCommentAsync(dto2);
        List<Comment> comments = await commentLogic.GetCommentsByPostIdAsync(1);
        Assert.That(comments.Count, Is.EqualTo(2));
        Assert.That(comments[0].body, Is.EqualTo("Test Comment 1"));
        Assert.That(comments[0].commentDate, Is.Not.EqualTo(null));
        Assert.That(comments[0].likeCount, Is.EqualTo(0));
        Assert.That(comments[0].commentId, Is.Not.EqualTo(0));
        Assert.That(comments[0].userId, Is.EqualTo(1));
        Assert.That(comments[0].postId, Is.EqualTo(1));
        Assert.That(comments[1].body, Is.EqualTo("Test Comment 2"));
        Assert.That(comments[1].commentDate, Is.Not.EqualTo(null));
        Assert.That(comments[1].likeCount, Is.EqualTo(0));
        Assert.That(comments[1].commentId, Is.Not.EqualTo(0));
        Assert.That(comments[1].userId, Is.EqualTo(1));
        Assert.That(comments[1].postId, Is.EqualTo(1));
    }
    
    [Test]
    public async Task getting_comments_by_user_id_returns_correct_comments()
    {
        CreateCommentDTO dto1 = new CreateCommentDTO("Test Comment 1", 1, 1);
        CreateCommentDTO dto2 = new CreateCommentDTO("Test Comment 2", 1, 1);
        await commentLogic.CreateCommentAsync(dto1);
        await commentLogic.CreateCommentAsync(dto2);
        List<Comment> comments = await commentLogic.GetCommentsByUserIdAsync(1);
        Assert.That(comments.Count, Is.EqualTo(2));
        Assert.That(comments[0].body, Is.EqualTo("Test Comment 1"));
        Assert.That(comments[0].commentDate, Is.Not.EqualTo(null));
        Assert.That(comments[0].likeCount, Is.EqualTo(0));
        Assert.That(comments[0].commentId, Is.Not.EqualTo(0));
        Assert.That(comments[0].userId, Is.EqualTo(1));
        Assert.That(comments[0].postId, Is.EqualTo(1));
        Assert.That(comments[1].body, Is.EqualTo("Test Comment 2"));
        Assert.That(comments[1].commentDate, Is.Not.EqualTo(null));
        Assert.That(comments[1].likeCount, Is.EqualTo(0));
        Assert.That(comments[1].commentId, Is.Not.EqualTo(0));
        Assert.That(comments[1].userId, Is.EqualTo(1));
        Assert.That(comments[1].postId, Is.EqualTo(1));
    }
    
    [Test]
    public async Task liking_comment_increases_like_count()
    {
        CreateCommentDTO dto = new CreateCommentDTO("Test Comment", 1, 1);
        await commentLogic.CreateCommentAsync(dto);
        Comment comment = await commentLogic.GetCommentAsync(1);
        Assert.DoesNotThrowAsync(() => commentLogic.LikeCommentAsync(comment.commentId, 1));
        Comment likedComment = await commentLogic.GetCommentAsync(1);
        Assert.That(likedComment.likeCount, Is.EqualTo(1));
    }

    [Test]
    public async Task unliking_comment_decrease_like_count()
    {
        CreateCommentDTO dto = new CreateCommentDTO("Test Comment", 1, 1);
        await commentLogic.CreateCommentAsync(dto);
        Comment comment = await commentLogic.GetCommentAsync(1);
        Assert.DoesNotThrowAsync(() => commentLogic.LikeCommentAsync(comment.commentId, 1));
        Assert.DoesNotThrowAsync(() => commentLogic.UnlikeCommentAsync(comment.commentId, 1));
        Comment unlikedComment = await commentLogic.GetCommentAsync(1);
        Assert.That(unlikedComment.likeCount, Is.EqualTo(0));
    }
    
    [Test]
    public async Task getting_all_liked_comments_of_desired_user_returns_correct_comments()
    {
        CreateCommentDTO dto1 = new CreateCommentDTO("Test Comment 1", 1, 1);
        CreateCommentDTO dto2 = new CreateCommentDTO("Test Comment 2", 1, 1);
        await commentLogic.CreateCommentAsync(dto1);
        await commentLogic.CreateCommentAsync(dto2);
        Comment comment1 = await commentLogic.GetCommentAsync(1);
        Comment comment2 = await commentLogic.GetCommentAsync(2);
        await commentLogic.LikeCommentAsync(comment1.commentId, 1);
        await commentLogic.LikeCommentAsync(comment2.commentId, 1);
        List<Comment> likedComments = await commentLogic.GetAllLikedCommentsAsync(1);
        Assert.That(likedComments.Count, Is.EqualTo(2));
        Assert.That(likedComments[0].body, Is.EqualTo("Test Comment 1"));
        Assert.That(likedComments[1].body, Is.EqualTo("Test Comment 2"));
    }
    
}