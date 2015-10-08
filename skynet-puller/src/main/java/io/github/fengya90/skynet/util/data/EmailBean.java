package io.github.fengya90.skynet.util.data;

/**
 * Created by fengya on 15-10-8.
 */
public class EmailBean{
    private String[] to;
    private String subject;
    private String msg;

    public EmailBean() {
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}