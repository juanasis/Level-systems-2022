package ar.edu.undec.level.controller.dto;

public class Response {
    private int status;
    private String message;
    private Object data;

    public Response() {
        this.status = 0;
        this.message = "";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
