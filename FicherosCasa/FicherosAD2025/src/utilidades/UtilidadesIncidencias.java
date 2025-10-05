package utilidades;

import excepciones.FechaNoValidaException;
import excepciones.NumeroNoValidoException;
import excepciones.UsuarioNoValidoException;
import modelo.Incidencia;
import modelo.ListaIncidencia;
import vista.Consola;
import vista.Escaner;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

public class UtilidadesIncidencias {
    static boolean MENU_PRINCIPAL_OK = false;

    private static void propagarNumeroNoValido(String mensaje, int numero) throws NumeroNoValidoException {
        throw new NumeroNoValidoException(
                String.format("%s. Dato Introducido: %d",mensaje, numero)
        );
    }

    public static int pedirOpcionMenuPrincipal() throws NumeroNoValidoException {
        int opcion;

        opcion = 0;
        try {
            if (Escaner.hasNext())
                Escaner.limpiarEscaner();
            opcion = Escaner.pedirEntero("Introduce una opcion (1-3): ");
            validarOpcionMenuPrincipal(opcion);
        } catch (InputMismatchException e) {
            propagarNumeroNoValido("La opcion del menu principal debe ser un numero entero valido", opcion);
            Escaner.limpiarEscaner();
        }
        return (opcion);
    }

    private static void validarOpcionMenuPrincipal(int numero) throws NumeroNoValidoException {
        if (numero < 1 || numero > 3) {
            propagarNumeroNoValido("La opcion del menu principal debe ser entre 1 y 3", numero);
            Escaner.limpiarEscaner();
        }
    }

    public static void insertarDato() throws NumeroNoValidoException {
        int dato;

        dato = -1;
        try {
            dato = Escaner.pedirEntero("Introduce un numero entero positivo multiplo de 3: ");
            validarDatoNumerico(dato);
        } catch (InputMismatchException e) {
            propagarNumeroNoValido("El dato debe ser un entero valido", dato);
            Escaner.limpiarEscaner();
        }
    }

    private static void validarDatoNumerico(int numero) throws NumeroNoValidoException {
        if (numero < 0)
            propagarNumeroNoValido("El dato debe ser un numero entero positivo", numero);
        if (numero % 3 != 0)
            propagarNumeroNoValido("El dato debe ser múltiplo de tres", numero);
    }

    public static void visualizarIncidencias(ListaIncidencia listaIncidencias) throws UsuarioNoValidoException, NumeroNoValidoException, FechaNoValidaException, InputMismatchException {
        int opcion;

        opcion = 0;
        Consola.mostrarMenu(List.of("Buscar por usuario.", "Buscar por rango de fecha."));
        try {
            opcion = Escaner.pedirEntero("Introduce una opcion (1-2): ");
            validarOpcionVisualizacion(opcion);
            switch (opcion) {
                case 1:
                    buscarPorUsuario(listaIncidencias);
                    break;
                case 2:
                    buscarPorRangoFechas(listaIncidencias);
                    break;
            }
        } catch (InputMismatchException e) {
            propagarNumeroNoValido("La opcion de visualizacion debe ser un numero entero valido", opcion);
        }
    }

    private static void validarOpcionVisualizacion(int numero) throws NumeroNoValidoException {
        if (numero < 1 || numero > 2)
            propagarNumeroNoValido("La opcion de visualizacion debe ser entre 1 y 2", numero);
    }

    private static void buscarPorUsuario(ListaIncidencia listaIncidencias) throws UsuarioNoValidoException {
        String              usuario;
        List<Incidencia>    listaFiltrada;

        usuario = UtilidadesUsuarios.pedirUsuario();
        listaFiltrada = listaIncidencias.buscarPorUsuario(usuario);
        for (Incidencia inicidencia : listaFiltrada)
            Consola.mostrarFrase(inicidencia.toString() + "\n", Colores.VERDE);
    }

    private static void buscarPorRangoFechas(ListaIncidencia listaIncidencias) throws FechaNoValidaException {
        LocalDate           desde;
        LocalDate           hasta;
        List<Incidencia>    listaFiltrada;

        desde = UtilidadesFechas.pedirFecha("Introduce la fecha inicial (yyyy/mm/dd): ");
        hasta = UtilidadesFechas.pedirFecha("Introduce la fecha final: (yyyy/mm/dd): ");
        listaFiltrada = listaIncidencias.buscarPorRangoFechas(desde, hasta);
        if (!listaFiltrada.isEmpty())
            Consola.mostrarFrase("INCIDENCIAS ENCONTRADAS: \n", Colores.VERDE);
        for (Incidencia incidencia : listaFiltrada)
            Consola.mostrarFrase(incidencia.toString() + "\n", Colores.VERDE);
    }
}
