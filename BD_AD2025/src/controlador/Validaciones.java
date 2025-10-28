package controlador;

import excepciones.FechaNoValidaException;
import excepciones.NumeroNoValidoException;
import excepciones.UsuarioNoValidoException;

public class Validaciones {
    public static boolean validar(String dato, int longitud) {
        return (dato.length() <= longitud);
    }

    public static void validarString(String dato, int longitudMinima, int longitudMaxima, String regex) throws UsuarioNoValidoException {
        if (dato == null)
            throw new UsuarioNoValidoException("El usuario no puede ser nulo.");
        if (dato.isEmpty())
            throw new UsuarioNoValidoException("El usuario no puede estar vacío.");
        if (dato.length() > longitudMaxima || dato.length() < longitudMinima)
            throw new UsuarioNoValidoException(String.format("El usuario debe tener entre %d y %d caracteres.", longitudMinima, longitudMaxima));
        if (!dato.matches(regex))
            throw new UsuarioNoValidoException(String.format("El usuario debe cuadrar con el patrón: (%s)", regex));
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

    public static void validarFecha(String fecha, String regex, int length) throws FechaNoValidaException {
        if (fecha.length() != length)
            throw new FechaNoValidaException(String.format("La longitud de la fecha debe ser: %d", length));
        if (!fecha.matches(regex))
            throw new FechaNoValidaException(String.format("La fecha debe cuadrar con el patrón: %s", regex));
    }
}
