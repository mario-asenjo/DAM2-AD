package controlador;


import vista.Consola;
import vista.Escaner;

public class ControladorIncidencias {
    public static String pedirUsuario() {
        String usuario;
        boolean userFlag;

        userFlag = true;
        usuario = "";
        while (userFlag || usuario.isEmpty()) {
            try {
                usuario = Escaner.pedirString("Introduce tu nombre de usuario:");
                userFlag = false;
            } catch (IllegalArgumentException e) {
                Consola.mostrarExcepcion(e);
            }
        }
        return (usuario);
    }
    public static void iniciar() {
        String usuario;

        usuario = pedirUsuario();
    }
}
