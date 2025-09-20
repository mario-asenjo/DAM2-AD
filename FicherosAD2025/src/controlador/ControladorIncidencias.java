package controlador;

import excepciones.NumeroNoValidoException;
import modelo.Incidencia;
import modelo.ListaIncidencia;
import repositorio.Fichero;
import repositorio.RepoIncidencias;
import utilidades.Colores;
import excepciones.UsuarioNoValidoException;
import utilidades.UtilidadesIncidencias;
import utilidades.UtilidadesUsuarios;
import vista.Consola;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ControladorIncidencias {
    static boolean EXIT = false;

    static Fichero<Incidencia> repo = new RepoIncidencias("Datos/incidencias.txt");

    public static void iniciar() {
        String  usuario;
        ListaIncidencia miLista;
        int     opcionMenuPrincipal;

        usuario = UtilidadesUsuarios.pedirUsuario();
        Consola.mostrarFrase("Usuario introducido correctamente.\n", Colores.AMARILLO);
        while (!EXIT) {
            try {
                miLista = new ListaIncidencia(repo.cargar());
                Consola.mostrarMenu(List.of("Insertar dato.\n", "Visualizar incidencias.\n", "Salir del programa.\n"));
                opcionMenuPrincipal = UtilidadesIncidencias.pedirOpcionMenuPrincipal();
                switch (opcionMenuPrincipal) {
                    case 1:
                        UtilidadesIncidencias.insertarDato();
                        break;
                    case 2:
                        UtilidadesIncidencias.visualizarIncidencias(miLista);
                        break;
                    case 3:
                        EXIT = true;
                        break;
                }
            } catch (IOException e) {
                Consola.mostrarExcepcion(e);
            } catch (NumeroNoValidoException e) {
                //Logica de ficheros
                Incidencia incidencia = new Incidencia(LocalDateTime.now(), e.getMessage(), usuario);
                Consola.mostrarExcepcion(e);
                try {
                    repo.guardar(incidencia);
                } catch (IOException y) {
                    Consola.mostrarExcepcion(y);
                }
            }

        }
    }
}
