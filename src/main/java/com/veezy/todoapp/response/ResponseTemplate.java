package com.veezy.todoapp.response;

public class ResponseTemplate<T> {
    private int statusCode;
    private String message;
    private T data;

    public ResponseTemplate(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseTemplate(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int status) {
        this.statusCode = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
