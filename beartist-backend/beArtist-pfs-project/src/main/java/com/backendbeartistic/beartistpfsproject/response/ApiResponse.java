package com.backendbeartistic.beartistpfsproject.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private boolean status;
}
