package vista;

import java.util.Scanner;

public class Escaner {
    static Scanner sc = new Scanner(System.in, "UTF-8");

    public static String pedirString(String frase) throws IllegalArgumentException{
        String input;

        Consola.mostrarFrase(frase);
        input = sc.nextLine();
        return (input);
    }


}
