package com.employees.crud.employees_crud.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private Integer code;
    private T data;

    // Constructors

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data, Integer code) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public static <T> ApiResponse<T> success(String message, T data, Integer code) {
        return new ApiResponse<>(true, message, data, code);
    }

    public static <T> ApiResponse<T> error(String message, Integer code) {
        return new ApiResponse<>(false, message, null, code);
    }

    public static <T> ApiResponse<T> error(String message, T data, Integer code) {
        return new ApiResponse<>(false, message, data, code);
    }
}