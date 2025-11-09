package org.example.infra.web.dto;

public class ImportDtos {
    public static class ImportRequest {
        public String filename;
        public ImportRequest() {}
    }
    public static class ImportResponse {
        public int importedQuestions;
        public int importedOptons;
        public ImportResponse() {}
        public ImportResponse(int q, int o) {
            this.importedQuestions = q;
            this.importedOptons = o;
        }
    }
}
