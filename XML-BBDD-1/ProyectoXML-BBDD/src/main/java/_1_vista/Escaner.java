package _1_vista;

import _6_excepciones.EntradaUsuarioNoValidaException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Supplier;

public class Escaner {
    static Scanner sc = new Scanner(System.in, "UTF-8");

    private static <T> T pedirDato(String frase, Supplier<T> lector) throws EntradaUsuarioNoValidaException {
        try {
            Consola.mostrarFrase(frase, Colores.AMARILLO);
            return lector.get();
        } catch (IllegalArgumentException | InputMismatchException e) {
            throw new EntradaUsuarioNoValidaException("Error al pedir entrada al usuario");
        }
    }

    public static String pedirString(String frase) throws EntradaUsuarioNoValidaException {
        return pedirDato(frase, () -> sc.nextLine());
    }

    public static int pedirEntero(String frase) throws InputMismatchException, EntradaUsuarioNoValidaException {
        int dato;
        dato = pedirDato(frase, () -> sc.nextInt());
        Escaner.limpiarEscaner();
        return (dato);
    }

    public static double pedirDouble(String frase) throws EntradaUsuarioNoValidaException {
        return pedirDato(frase, () -> sc.nextDouble());
    }

    public static char pedirChar(String frase) throws EntradaUsuarioNoValidaException {
        char c;

        c = pedirDato(frase, () -> sc.next().charAt(0));
        Escaner.limpiarEscaner();
        return c;
    }

    public static boolean pedirBoolean(String frase) throws EntradaUsuarioNoValidaException {
        String dato = pedirDato(frase, () -> sc.nextLine());
        if (!(dato.equalsIgnoreCase("si") || dato.equalsIgnoreCase("no"))) {
            throw new EntradaUsuarioNoValidaException("El string utilizado debe ser si o no!.");
        }
        return (dato.equalsIgnoreCase("si"));
    }

    public static void limpiarEscaner() {
        sc.nextLine();
    }
}
