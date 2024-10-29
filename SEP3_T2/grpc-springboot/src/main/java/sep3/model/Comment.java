package sep3.model;

import java.sql.Date;

public class Comment
{
    public String body;
    public SocialMediaUser author;
    public Post post;
    public Date commentDate;

    public Comment(String body, SocialMediaUser author, Post post, Date commentDate)
    {
        this.body = body;
        this.author = author;
        this.post = post;
        this.commentDate = commentDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public SocialMediaUser getAuthor() {
        return author;
    }

    public void setAuthor(SocialMediaUser author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public void addComment(Comment comment)
    {
        post.comments.add(comment);
        post.commentsCount++;
    }

    public void removeComment(Comment comment)
    {
        post.comments.remove(comment);
        post.commentsCount--;
    }

    public void reportComment()
    {
        post.isReported = true;
    }

    public void likeComment()
    {
        post.likesCount++;
    }

    public void dislikeComment()
    {
        post.likesCount--;
    }




}

