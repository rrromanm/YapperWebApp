namespace DTOs.Models;

public class Comment
{
    public string body { get; set; }
    public string commentDate { get; set; } 
    public int likeCount { get; set; }
    public int commentId { get; set; }
    public int userId { get; set; }
    public int postId { get; set; }
    
    public bool userLiked { get; set; }
    
    public Comment(string body, string commentDate, int likeCount, int commentId, int userId, int postId)
    {
        this.body = body;
        this.commentDate = commentDate;
        this.likeCount = likeCount;
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
    }
    public override string ToString()
    {
        return $"Body: {body}, Comment Date: {commentDate}, Like Count: {likeCount}, Comment ID: {commentId}, SMUser ID: {userId}, Post ID: {postId}";
    }
    protected bool Equals(Comment other)
    {
        return body == other.body && commentDate == other.commentDate && likeCount == other.likeCount && commentId == other.commentId && userId == other.userId && postId == other.postId;
    }
    
    public override bool Equals(object obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((Comment) obj);
    }
    public override int GetHashCode()
    {
        return HashCode.Combine(body, commentDate, likeCount, commentId, userId, postId);
    }
}