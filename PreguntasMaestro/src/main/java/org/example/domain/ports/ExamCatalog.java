package org.example.domain.ports;

import java.util.List;

public interface ExamCatalog {
    List<ExamenMeta> listar();
    record ExamenMeta(String nombre, long bytes, long lastModified) {}
}
