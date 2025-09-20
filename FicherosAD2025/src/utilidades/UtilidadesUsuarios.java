package utilidades;

import excepciones.UsuarioNoValidoException;
import vista.Consola;
import vista.Escaner;

public class UtilidadesUsuarios {
    static boolean USER_OK;

    public static String pedirUsuario() {
        String input;

        USER_OK = false;
        input = null;
        while (!USER_OK)
        {
            try {
                input = Escaner.pedirString("Introduce tu usuario: ");
                validarUsuario(input);
                USER_OK = true;
            } catch (UsuarioNoValidoException e) {
                Consola.mostrarExcepcion(e);
            }
        }
        return (input);
    }

    public static void validarUsuario(String usuario) {
        if (usuario == null)
            throw new UsuarioNoValidoException("El usuario no puede ser nulo.");
        usuario = usuario.trim();
        if (usuario.isEmpty())
            throw new UsuarioNoValidoException("El usuario no puede estar vacío.");
        if (!usuario.matches("[a-zA-Z]+"))
            throw new UsuarioNoValidoException("El usuario debe contener solo carácteres alfabéticos. (ejemplo -> masenper)");
        if (usuario.length() < 4 || usuario.length() > 10)
            throw new UsuarioNoValidoException("El usuario no puede tener menos de tres o más de diez carácteres.");
    }
}
