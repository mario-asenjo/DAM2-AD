package org.example.adapter.cli.view;

import org.example.adapter.cli.view.errors.StringNoValidoException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Supplier;

public class Escaner {
    static Scanner sc = new Scanner(System.in, "UTF-8");

    private static <T> T pedirDato(String frase, Supplier<T> lector) {
        Consola.mostrarFrase(frase, Colores.AMARILLO);
        return lector.get();
    }

    public static String pedirString(String frase) {
        return pedirDato(frase, () -> sc.nextLine());
    }

    public static int pedirEntero(String frase) throws InputMismatchException {
        int dato;
        dato = pedirDato(frase, () -> sc.nextInt());
        Escaner.limpiarEscaner();
        return (dato);
    }

    public static double pedirDouble(String frase) throws InputMismatchException {
        return pedirDato(frase, () -> sc.nextDouble());
    }

    public static char pedirChar(String frase) {
        char c;

        c = pedirDato(frase, () -> sc.next().charAt(0));
        Escaner.limpiarEscaner();
        return c;
    }

    public static boolean pedirBoolean(String frase) throws StringNoValidoException {
        String dato = pedirDato(frase, () -> sc.nextLine());
        if (!(dato.equalsIgnoreCase("si") || dato.equalsIgnoreCase("no"))) {
            throw new StringNoValidoException("El string utilizado debe ser si o no!.");
        }
        return (dato.equalsIgnoreCase("si"));
    }

    public static void limpiarEscaner() {
        sc.nextLine();
    }

    public static boolean hasNext() {
        return sc.hasNext();
    }
}
