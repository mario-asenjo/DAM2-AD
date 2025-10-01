package controlador;


import excepciones.FechaNoValidaException;
import excepciones.LecturaEscrituraException;
import excepciones.NumeroNoValidoException;
import excepciones.UsuarioNoValidoException;
import modelo.Incidencia;
import modelo.ListaIncidencia;
import repositorio.Fichero;
import servicio.ServicioFichero;
import vista.Colores;
import vista.Consola;
import vista.Escaner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ControladorIncidencias {
    public static void iniciar() {
        String          usuario;
        String          usuarioBusqueda;
        String          fechaBusquedaInicial;
        String          fechaBusquedaFinal;
        int             opcionMenuPrincipal;
        int             opcionVisualizacion;
        boolean         userOK;
        boolean         exit;
        ServicioFichero servicioFichero;
        ListaIncidencia listaIncidencias;
        List<Incidencia> listaFiltrada;

        usuario = null;
        userOK = false;
        exit = false;
        servicioFichero = new ServicioFichero("Datos/incidencias.txt");
        listaIncidencias = null;
        do {
            try {
                usuario = Escaner.pedirString("Introduce tu usuario: ");
                Validaciones.validarString(usuario, 3, 10, "^[a-zA-Z]+");
                userOK = true;
            } catch (UsuarioNoValidoException e) {
                Consola.mostrarExcepcion(e);
            }
        } while (!userOK);
        do {
            try {
                listaIncidencias = new ListaIncidencia(servicioFichero.leer());
            } catch (LecturaEscrituraException e) {
                Consola.mostrarExcepcion(e);
            }
            try {
                Consola.mostrarMenu(List.of("Introduce un dato", "Visualizar incidencias", "Salir del programa"));
                opcionMenuPrincipal = Escaner.pedirEntero("Introduce una opcion (1-3):");
                Validaciones.validarEntero(Integer.toString(opcionMenuPrincipal), "^[1-3]+");
                switch (opcionMenuPrincipal) {
                    case 1:
                        Validaciones.validarEntero(Integer.toString(Escaner.pedirEntero("Dame un dato numerico del 0 al 9:")), "[0-9]");
                        break;
                    case 2:
                        Consola.mostrarMenu(List.of("Buscar por usuario.", "Buscar por rango de fecha."));
                        opcionVisualizacion = Escaner.pedirEntero("Introduce una opcion (1-2): ");
                        Validaciones.validarEntero(Integer.toString(opcionVisualizacion), "[1-2]");
                        switch (opcionVisualizacion) {
                            case 1:
                                usuarioBusqueda = Escaner.pedirString("Introduce un usuario a buscar: ");
                                listaFiltrada = listaIncidencias.buscarPorUsuario(usuarioBusqueda);
                                for (Incidencia incidencia : listaFiltrada) {
                                    Consola.mostrarFraseEndl(incidencia.toString(), Colores.VERDE);
                                }
                                break;
                            case 2:
                                fechaBusquedaInicial = Escaner.pedirString("Introduce una fecha inicial con el siguiente formato: ");
                                Validaciones.validarFecha(fechaBusquedaInicial, "^[0-9]{4}/(?:(?:0[13578]|1[02])/(?:0[1-9]|[12][0-9]|3[01])|(?:0[469]|11)/(?:0[1-9]|[12][0-9]|30)|02/(?:0[1-9]|1[0-9]|2[0-9]))$", 10);
                                fechaBusquedaFinal = Escaner.pedirString("Introduce una fecha final con el siguiente formato: ");
                                Validaciones.validarFecha(fechaBusquedaFinal, "^[0-9]{4}/(?:(?:0[13578]|1[02])/(?:0[1-9]|[12][0-9]|3[01])|(?:0[469]|11)/(?:0[1-9]|[12][0-9]|30)|02/(?:0[1-9]|1[0-9]|2[0-9]))$", 10);
                                listaFiltrada = listaIncidencias.buscarPorRangoFechas(LocalDate.parse(fechaBusquedaInicial), LocalDate.parse(fechaBusquedaFinal));
                                for (Incidencia incidencia : listaFiltrada) {
                                    Consola.mostrarFraseEndl(incidencia.toString(), Colores.VERDE);
                                }
                        }
                }
            } catch (NumeroNoValidoException | UsuarioNoValidoException | FechaNoValidaException e) {
                Incidencia incidencia;

                incidencia = new Incidencia(LocalDateTime.now(), usuario, e.getMessage());
                if (incidencia != null) {
                    try {
                        servicioFichero.guardar(incidencia.toFileString());
                    } catch (LecturaEscrituraException eLectura) {
                        Consola.mostrarExcepcion(eLectura);
                    }
                }
            }
        } while (!exit);
    }

}
