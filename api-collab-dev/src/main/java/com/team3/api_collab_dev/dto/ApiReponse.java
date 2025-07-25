package com.team3.api_collab_dev.dto;

public record ApiReponse<T>(
        String code ,
        String message ,
        T data) {

}
