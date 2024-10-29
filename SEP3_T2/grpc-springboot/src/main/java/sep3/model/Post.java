package sep3.model;

import java.sql.Date;
import java.util.ArrayList;

public class Post
{
    public SocialMediaUser user;
    public String title;
    public String body;
    public Date postDate;
    public int likesCount;
    public int commentsCount;
    public String category;
    public ArrayList<Comment> comments;
    public boolean isReported;

    public Post(SocialMediaUser user, String title, String body, Date postDate, String category)
    {
        this.user = user;
        this.title = title;
        this.body = body;
        this.postDate = postDate;
        this.likesCount = 0;
        this.commentsCount = 0;
        this.category = category;
        this.comments = new ArrayList<Comment>();
        isReported = false;
    }
    public SocialMediaUser getUser() {
        return user;
    }

    public void setUser(SocialMediaUser user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

}
