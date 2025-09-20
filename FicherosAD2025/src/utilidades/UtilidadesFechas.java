package utilidades;

import excepciones.FechaNoValidaException;
import vista.Consola;
import vista.Escaner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class UtilidadesFechas {
    static boolean DATE_OK;
    public static LocalDate pedirFecha(String frase) throws FechaNoValidaException {
        DATE_OK = false;
        String fechaString;
        LocalDate fecha;

        fecha = null;
        while (!DATE_OK) {
            try {
                fechaString = Escaner.pedirString(frase);
                fecha = validarFecha(fechaString);
                DATE_OK = true;
            } catch (FechaNoValidaException e) {
                Consola.mostrarExcepcion(e);
            }
        }
        return fecha;
    }

    private static LocalDate validarFecha(String frase) {
        String regexFechaValidaPorMes;
        DateTimeFormatter formatter;
        LocalDate fecha;

        regexFechaValidaPorMes = "^[0-9]{4}/(?:(?:0[13578]|1[02])/(?:0[1-9]|[12][0-9]|3[01])|(?:0[469]|11)/(?:0[1-9]|[12][0-9]|30)|02/(?:0[1-9]|1[0-9]|2[0-9]))$";
        formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);
        if (!frase.matches(regexFechaValidaPorMes)) {
            throw new FechaNoValidaException("La fecha debe ser valida y con el siguiente formato (yyyy/mm/dd). Introducido: " + frase);
        }
        try {
            fecha = LocalDate.parse(frase, formatter);
        }catch (DateTimeParseException e) {
            throw new FechaNoValidaException("La fecha introducida de un 29 de Febrero es de un año que no es bisiesto");
        }
        return (fecha);
    }
}
