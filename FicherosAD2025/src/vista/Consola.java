package vista;

import utilidades.Colores;

import java.util.List;

public class Consola {
    public static void mostrarFrase(String frase) {
        System.out.printf("[+] %s", frase);
    }

    public static void mostrarFrase(String frase, Colores color) {
        System.out.printf("%s", color.aplicar_colores("[+] " + frase));
    }

    public static void mostrarExcepcion(Exception e) {
        System.out.printf("%s\n", Colores.ROJO.aplicar_colores("[!] Excepcion: " + e.getMessage()));
    }

    public static void mostrarMenu(List<String> opciones) {
        mostrarFrase("[#] OPCIONES\n", Colores.AMARILLO);
        for (int i = 1; i <= opciones.size(); i++) {
            mostrarFrase(String.format("[%d] %s", i, opciones.get(i - 1)), Colores.AMARILLO);
        }
    }
}
