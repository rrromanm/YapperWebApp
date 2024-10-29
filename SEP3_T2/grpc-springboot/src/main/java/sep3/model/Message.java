package sep3.model;

import java.util.Date;

public class Message
{
    public String body;
    public Date date;
    public SocialMediaUser sender;
    public SocialMediaUser receiver;

    public Message(String body, Date date, SocialMediaUser sender, SocialMediaUser receiver)
    {
        this.body = body;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }
}
