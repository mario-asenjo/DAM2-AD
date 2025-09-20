package utilidades;

public enum Colores {
    // Reset
    RESET("\u001B[0m"),

    // Colores de texto
    NEGRO("\u001B[30m"),
    ROJO("\u001B[31m"),
    VERDE("\u001B[32m"),
    AMARILLO("\u001B[33m"),
    AZUL("\u001B[34m"),
    MORADO("\u001B[35m"),
    CIAN("\u001B[36m"),
    BLANCO("\u001B[37m"),

    // Colores de fondo
    FONDO_NEGRO("\u001B[40m"),
    FONDO_ROJO("\u001B[41m"),
    FONDO_VERDE("\u001B[42m"),
    FONDO_AMARILLO("\u001B[43m"),
    FONDO_AZUL("\u001B[44m"),
    FONDO_MORADO("\u001B[45m"),
    FONDO_CIAN("\u001B[46m"),
    FONDO_BLANCO("\u001B[47m");

    private final String codigo;

    Colores(String codigo) {
        this.codigo = codigo;
    }

    public String aplicar_colores(String texto) {
        return this.codigo + texto + RESET.codigo;
    }

    @Override
    public String toString() {
        return this.codigo;
    }
}
