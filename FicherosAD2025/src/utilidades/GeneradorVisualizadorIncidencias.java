package utilidades;

import excepciones.FechaNoValidaException;
import excepciones.NumeroNoValidoException;
import excepciones.UsuarioNoValidoException;
import modelo.Incidencia;
import modelo.ListaIncidencia;
import repositorio.Fichero;
import vista.Consola;
import vista.Escaner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;

public class GeneradorVisualizadorIncidencias {
    public static String login() {
        String  input;
        boolean loginOK;

        loginOK = false;
        input = null;
        do {
            try {
                input = UtilidadesUsuarios.pedirUsuario();
                Consola.mostrarFraseEndl("Usuario introducido correctamente.", Colores.AMARILLO);
                loginOK = true;
            } catch (UsuarioNoValidoException e) {
                Consola.mostrarExcepcion(e);
            }
        } while (!loginOK);
        return (input);
    }

    public static void mainLoop(Fichero<Incidencia> repo, String usuario) {
        ListaIncidencia miLista;
        int             opcionMenuPrincipal;
        boolean         exitFlag;

        exitFlag = false;
        do {
            try {
                miLista = new ListaIncidencia(repo.cargar());
                Consola.mostrarMenu(List.of("Insertar dato.", "Visualizar incidencias.", "Salir del programa."));
                opcionMenuPrincipal = UtilidadesIncidencias.pedirOpcionMenuPrincipal();
                switch (opcionMenuPrincipal) {
                    case 1 -> UtilidadesIncidencias.insertarDato();
                    case 2 -> UtilidadesIncidencias.visualizarIncidencias(miLista);
                    case 3 -> exitFlag = true;
                }
            } catch (IOException e) {
                Consola.mostrarExcepcion(e);
            } catch (NumeroNoValidoException | UsuarioNoValidoException | FechaNoValidaException e) {
                Incidencia incidencia;

                incidencia = new Incidencia(LocalDateTime.now(), e.getMessage(), usuario);
                Consola.mostrarExcepcion(e);
                try {
                    repo.guardar(incidencia);
                } catch (IOException y) {
                    Consola.mostrarExcepcion(y);
                }
            }
        } while (!exitFlag);
    }
}
