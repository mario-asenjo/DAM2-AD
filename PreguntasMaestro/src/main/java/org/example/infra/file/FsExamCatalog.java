package org.example.infra.file;

import org.example.domain.ports.ExamCatalog;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FsExamCatalog implements ExamCatalog {
    private final Path baseDir;
    private final String extension;

    public FsExamCatalog(Path baseDir, String extension) {
        this.baseDir = baseDir;
        this.extension = extension.startsWith(".") ? extension : "." + extension;
    }

    @Override
    public List<ExamenMeta> listar() {
        List<ExamenMeta> lista = new ArrayList<>();
        if (!Files.isDirectory(baseDir)) {
            return lista; // sin excepciones ruidosas
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(baseDir, "*" + extension)) {
            for (Path p : stream) {
                // ignorar subdirectorios
                if (Files.isDirectory(p)) continue;

                try {
                    BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
                    String nombre = p.getFileName().toString();
                    long size = attrs.size();
                    long lastMod = attrs.lastModifiedTime().toMillis();

                    lista.add(new ExamenMeta(nombre, size, lastMod));
                } catch (IOException e) {
                    // si un archivo da error de lectura, lo saltamos
                }
            }
        } catch (IOException e) {
            // si el directorio no es accesible, devolvemos lista vacía
        }
        return lista;
    }
}
