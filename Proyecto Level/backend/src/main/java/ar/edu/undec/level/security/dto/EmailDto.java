package ar.edu.undec.level.security.dto;


public class EmailDto {
    private String mailFrom;
    private String mailTo;
    private String subject;
    private String username;
    private String token;

    public EmailDto() {
    }

    public EmailDto(String mailFrom, String mailTo, String subject, String username, String token) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.subject = subject;
        this.username = username;
        this.token = token;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
