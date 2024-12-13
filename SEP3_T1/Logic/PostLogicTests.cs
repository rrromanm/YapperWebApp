using NUnit.Framework;
using App.Logic;
using DTOs.Models;
using DTOs.User.PostDTOs;
using System.Collections.Generic;
using System.Threading.Tasks;
using GrpcClient;

namespace Logic
{
    public class PostLogicTests
    {
        private PostLogic postLogic;

        [SetUp]
        public void Setup()
        {
            postLogic = new PostLogic(new GRPCService());
        }

        [Test]
        public async Task creating_post_adds_it_to_database()
        {
            CreatePostDTO dto = new CreatePostDTO("Test Post", "This is a test post.", 1, 1);
            Assert.DoesNotThrow(() => postLogic.CreatePost(dto));
            Post post = await postLogic.GetPost(1);
            Assert.That(post.Title, Is.EqualTo("Test Post"));
            Assert.That(post.Body, Is.EqualTo("This is a test post."));
            Assert.That(post.CategoryId, Is.EqualTo(1));
            Assert.That(post.UserId, Is.EqualTo(1));
            Assert.That(post.PostId, Is.Not.EqualTo(0));
        }

        [Test]
        public async Task updating_post_updates_it_in_database()
        {
            CreatePostDTO dto = new CreatePostDTO("Test Post", "This is a test post.", 1, 1);
            await postLogic.CreatePost(dto);
            Post post = await postLogic.GetPost(1);
            UpdatePostDTO updateDto = new UpdatePostDTO(post.PostId, "Updated Test Post", "This is an updated test post.", 1, post.UserId);
            Assert.DoesNotThrow(() => postLogic.UpdatePost(updateDto));
            Post updatedPost = await postLogic.GetPost(post.PostId);
            Assert.That(updatedPost.Title, Is.EqualTo("Updated Test Post"));
            Assert.That(updatedPost.Body, Is.EqualTo("This is an updated test post."));
            Assert.That(updatedPost.CategoryId, Is.EqualTo(1));
            Assert.That(updatedPost.UserId, Is.EqualTo(1));
            Assert.That(updatedPost.PostId, Is.EqualTo(post.PostId));
        }

        [Test]
        public async Task deleting_post_deletes_it_from_database()
        {
            CreatePostDTO dto = new CreatePostDTO("Test Post", "This is a test post.", 1, 1);
            await postLogic.CreatePost(dto);
            Post post = await postLogic.GetPost(1);
            Assert.DoesNotThrow(() => postLogic.DeletePost(post.PostId));
            Assert.Throws<KeyNotFoundException>(() => postLogic.GetPost(post.PostId));
        }

        [Test]
        public async Task getting_post_by_id_returns_correct_post()
        {
            CreatePostDTO dto = new CreatePostDTO("Test Post", "This is a test post.", 1, 1);
            await postLogic.CreatePost(dto);
            Post post = await postLogic.GetPost(1);
            Assert.That(post.Title, Is.EqualTo("Test Post"));
            Assert.That(post.Body, Is.EqualTo("This is a test post."));
            Assert.That(post.CategoryId, Is.EqualTo(1));
            Assert.That(post.UserId, Is.EqualTo(1));
            Assert.That(post.PostId, Is.Not.EqualTo(0));
        }

        [Test]
        public async Task getting_all_posts_returns_all_posts()
        {
            CreatePostDTO dto1 = new CreatePostDTO("Test Post 1", "This is test post 1.", 1, 1);
            await postLogic.CreatePost(dto1);
            CreatePostDTO dto2 = new CreatePostDTO("Test Post 2", "This is test post 2.", 1, 1);
            await postLogic.CreatePost(dto2);
            List<Post> posts = await postLogic.GetAllPosts();
            Assert.That(posts.Count, Is.EqualTo(2));
            Assert.That(posts[0].Title, Is.EqualTo("Test Post 1"));
            Assert.That(posts[1].Title, Is.EqualTo("Test Post 2"));
        }
    }
}