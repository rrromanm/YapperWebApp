namespace DTOs.Models;

public class Message
{
    private int id;
    private string content;
    private int senderId;
    private int receiverId;
    private DateTime date;
    
    public Message(int id, string content, int senderId, int receiverId, DateTime date)
    {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = date;
    }
    
    public string toString()
    {
        return "Message: " + content + " with id: " + id + " sent by: " + senderId + " to: " + receiverId + " on: " + date;
    }
    
    protected bool Equals(Message other)
    {
        return id == other.id && content == other.content && senderId == other.senderId && receiverId == other.receiverId && date == other.date;
    }
    
    public override bool Equals(object obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((Message) obj);
    }
    
    public override int GetHashCode()
    {
        return HashCode.Combine(id, content, senderId, receiverId, date);
    }
}