package com.App.Yogesh.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)  {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        System.out.println("commence method called ");
        //ErrorResponse errorResponse = new ErrorResponse("Unauthorized: You must be logged in to access this resource.");
        //JSONObject jsonObject;
       // try {
           // jsonObject = new JSONObject(errorResponse.toString());
           // jsonObject.put("success",false);

//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        // Set the response content type to JSON
        response.setContentType("application/json");
        // Write the JSON response to the response body
        //response.getWriter().println(jsonObject);
       // response.getWriter().flush();


    }
}