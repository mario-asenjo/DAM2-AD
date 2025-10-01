package vista;

<<<<<<< HEAD
import vista.Colores;
=======
import utilidades.Colores;
>>>>>>> 44ac764ec9ee34501041d0ca3c22ac07871f22c0

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

    public static void limpiarEscaner() {
        sc.nextLine();
    }

    public static boolean hasNext() {
        return sc.hasNext();
    }
}
