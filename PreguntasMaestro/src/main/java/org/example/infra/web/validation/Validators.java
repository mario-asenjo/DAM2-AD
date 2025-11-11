package org.example.infra.web.validation;

import java.util.Locale;

public class Validators {
    private Validators() {}

    public static int mustBeIntInRange(String raw, int min, int max, String field) {
        if (raw == null) {
            throw new IllegalArgumentException(field + " es obligatorio.");
        }
        int v;
        try {
            v = Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(field + " debe ser un entero");
        }
        if (v < min || v > max) {
            throw new IllegalArgumentException(field + " debe ser entre " + min + " y "+ max);
        }
        return v;
    }

    public static String mustBeNonBlank(String s, String field) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " es obligatorio");
        }
        return s.trim();
    }

    public static char mustBeOptionABCD(String s, String field) {
        s = mustBeNonBlank(s, field);
        if (s.length() != 1) {
            throw new IllegalArgumentException(field + " debe ser una letra A/B/C/D");
        }
        char c = Character.toUpperCase(s.charAt(0));
        if (c != 'A' && c != 'B' && c != 'C' && c != 'D') {
            throw new IllegalArgumentException(field + " debe ser A/B/C/D");
        }
        return c;
    }

    public static String mustBeTxtFile(String filename) {
        filename = mustBeNonBlank(filename, "filename");
        if (filename.contains("..")) {
            throw new IllegalArgumentException("filename invalido");
        }
        if (!filename.toLowerCase().endsWith(".txt")) {
            throw new IllegalArgumentException("el filename debe terminar con .txt");
        }
        return filename;
    }
}
