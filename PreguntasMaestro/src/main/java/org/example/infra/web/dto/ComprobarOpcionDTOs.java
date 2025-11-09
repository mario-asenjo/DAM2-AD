package org.example.infra.web.dto;

public class ComprobarOpcionDTOs {
    public static class ComprobarOpcionRequest {
        public long preguntaId;
        public String opcion;
        public ComprobarOpcionRequest() {}
    }

    public static class ComprobarOpcionResponse {
        public boolean esCorrecta;
        public ComprobarOpcionResponse() {}
        public ComprobarOpcionResponse(boolean esCorrecta) {
            this.esCorrecta = esCorrecta;
        }
    }
}
