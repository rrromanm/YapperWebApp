﻿namespace DTOs.Models;

public class Post
{
    public string Title { get; set; }
    public string Body { get; set; }
    public int LikeCount { get; set; }
    public int CommentCount { get; set; }
    public string PostDate { get; set; }
    public int CategoryId { get; set; }
    public int PostId { get; set; }
    public int UserId { get; set; }
    public bool UserLiked { get; set; }

    public Post(string title, string body, int likeCount, int commentCount, string postDate, int categoryId ,int postId ,int userId)
    {
        Title = title;
        Body = body;
        LikeCount = likeCount;
        CommentCount = commentCount;
        PostDate = postDate;
        CategoryId = categoryId;
        PostId = postId;
        UserId = userId;
    }
}