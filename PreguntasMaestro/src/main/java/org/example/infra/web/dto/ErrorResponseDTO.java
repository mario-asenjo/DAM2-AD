package org.example.infra.web.dto;

public class ErrorResponseDTO {
    public String error;
    public String mensaje;
    public ErrorResponseDTO() {}
    public ErrorResponseDTO(String error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
    }
}
