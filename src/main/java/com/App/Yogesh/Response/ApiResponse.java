package com.App.Yogesh.Response;

/**
 * Represents a response structure for API responses,
 * containing a message, status, and optional data.
 *
 * @param <T> the type of the response data
 */
public class  ApiResponse<T> {

    private String message;
    private int status;
    private T data; // Generic data field to hold the response data

    // Default constructor
    public ApiResponse() {}

    // Parameterized constructor
    public ApiResponse(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    // Retrieves the status of the API response.
    public int getStatus() {
        return status;
    }

    // Sets the status of the API response.
    public void setStatus(int status) {
        this.status = status;
    }

    // Retrieves the message associated with the API response.
    public String getMessage() {
        return message;
    }

    // Sets the message associated with the API response.
    public void setMessage(String message) {
        this.message = message;
    }

    // Retrieves the data associated with the API response.
    public T getData() {
        return data;
    }

    // Sets the data associated with the API response.
    public void setData(T data) {
        this.data = data;
    }
}
