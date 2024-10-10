package com.App.Yogesh.Response;

/**
 * Represents a  response structure for API responses,
 * containing a message and a status .
 */
public class ApiResponse {

    private String message;
    private boolean status;

    // Default constructor
    public ApiResponse() {}

    // Parameterized constructor
    public ApiResponse(String message, boolean status) {
        super();
        this.message = message;
        this.status = status;
    }


     // Retrieves the status of the API response.

    public boolean getStatus() {
        return status;
    }

  // Sets the status of the API response.

    public void setStatus(boolean status) {
        this.status = status;
    }

   // Retrieves the message associated with the API response.

    public String getMessage() {
        return message;
    }

  //Sets the message associated with the API response.

    public void setMessage(String message) {
        this.message = message;
    }
}
