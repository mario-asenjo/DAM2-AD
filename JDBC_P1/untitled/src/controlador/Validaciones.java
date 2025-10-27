package controlador;

import excepciones.NumeroNoValidoException;
import excepciones.StringNoValidoException;

public class Validaciones {
    public static boolean validar(String dato, int longitud) {
        return (dato.length() <= longitud);
    }

    public static void validarString(String dato, int longitudMinima, int longitudMaxima, String regex) throws StringNoValidoException {
        if (dato == null)
            throw new StringNoValidoException("El usuario no puede ser nulo.");
        if (dato.isEmpty())
            throw new StringNoValidoException("El usuario no puede estar vacío.");
        if (dato.length() > longitudMaxima || dato.length() < longitudMinima)
            throw new StringNoValidoException(String.format("El usuario debe tener entre %d y %d caracteres.", longitudMinima, longitudMaxima));
        if (!dato.matches(regex))
            throw new StringNoValidoException(String.format("El usuario debe cuadrar con el patrón: (%s)", regex));
    }

    public static void validarEntero(String numero, String regex) {
        try {
            Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            throw new NumeroNoValidoException(String.format("El numero no es un numero valido: %s", numero));
        }
        if (!numero.matches(regex))
            throw new NumeroNoValidoException(String.format("El numero debe cuadrar con el patrón: (%s)", regex));
    }

}
