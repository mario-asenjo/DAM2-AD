package org.example.infra.web.dto;

public class ExamDTO {
    public String name;
    public long bytes;
    public long lastModified;

    public ExamDTO() {

    }

    public ExamDTO(String name, long bytes, long lastModified) {
        this.name = name;
        this.bytes = bytes;
        this.lastModified = lastModified;
    }
}
