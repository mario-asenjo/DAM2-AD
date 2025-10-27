package vista;

import java.util.List;

public class Consola {
    public static void mostrarFrase(String frase) {
        System.out.printf("[+] %s", frase);
    }

    public static void mostrarFrase(String frase, Colores color) {
        System.out.printf("%s", color.aplicar_colores("[+] " + frase));
    }

    public static void mostrarFraseSinInicio(String frase, Colores color) {
        System.out.printf("%s", color.aplicar_colores(frase));
    }

    public static void mostrarFraseEndl(String frase) {
        mostrarFrase(frase + "\n");
    }

    public static void mostrarFraseEndl(String frase, Colores color) {
        mostrarFrase(frase + "\n", color);
    }

    public static void mostrarExcepcion(Exception e) {
        mostrarFraseEndl("[!] Excepcion: " + e.getMessage(), Colores.ROJO);
    }

    public static void mostrarMenu(List<String> opciones) {
        mostrarFraseEndl("[#] OPCIONES", Colores.AMARILLO);
        for (int i = 1; i <= opciones.size(); i++) {
            mostrarFraseEndl(String.format("[%d] %s", i, opciones.get(i - 1)), Colores.AMARILLO);
        }
    }

    public static void mostrarArrayListString(List<String> listaAMostrar) {
        mostrarFraseEndl("[+] Lista Informativa:");
        for (int i = 0; i < listaAMostrar.size(); i++) {
            mostrarFraseEndl(String.format("[%d] %s", i,  listaAMostrar.get(i)), Colores.AMARILLO);
        }
    }
}
