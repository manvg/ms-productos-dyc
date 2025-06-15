package com.microservicio.ms_productos_dyc.model.dto;

public class ResponseModelDTO {
    private boolean status;
    private String message;

    public ResponseModelDTO(boolean status, String message){
        this.status = status;
        this.message = message;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
