package vista;

public class Consola {
    public static void mostrarFrase(String frase) {
        System.out.printf("[+] %s ", frase);
    }

    public static void mostrarExcepcion(Exception e) {
        System.err.printf("Excepcion: %s", e.getMessage());
    }
}
