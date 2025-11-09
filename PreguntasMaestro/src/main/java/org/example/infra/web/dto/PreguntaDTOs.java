package org.example.infra.web.dto;

import java.util.List;

public class PreguntaDTOs {
    public static class OpcionDTO {
        public String opcion;
        public String texto;
        public OpcionDTO() {}
        public OpcionDTO(String opcion, String texto) {
            this.opcion = opcion;
            this.texto = texto;
        }

        public static class PreguntaDTO {
            public long id;
            public String enunciado;
            public List<OpcionDTO> opciones;
        }
    }
}
