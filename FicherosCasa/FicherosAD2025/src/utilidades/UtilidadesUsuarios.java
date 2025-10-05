package utilidades;

import excepciones.NumeroNoValidoException;
import excepciones.UsuarioNoValidoException;
import vista.Consola;
import vista.Escaner;

public class UtilidadesUsuarios {
    private static void propagarUsuarioNoValido(String mensaje, String usuario) throws UsuarioNoValidoException {
        throw new UsuarioNoValidoException(
                String.format("%s. Usuario Introducido: %s",mensaje, usuario)
        );
    }

    public static String pedirUsuario() {
        boolean userOK;
        String input;

        userOK = false;
        input = null;
        while (!userOK)
        {
            try {
                input = Escaner.pedirString("Introduce tu usuario: ");
                validarUsuario(input);
                userOK = true;
            } catch (UsuarioNoValidoException e) {
                Consola.mostrarExcepcion(e);
                propagarUsuarioNoValido(e.getLocalizedMessage(), input);
            }
        }
        return (input);
    }

    public static void validarUsuario(String usuario) {
        if (usuario == null)
            propagarUsuarioNoValido("El usuario no puede ser nulo.", usuario);
        usuario = usuario.trim();
        if (usuario.isEmpty())
            propagarUsuarioNoValido("El usuario no puede estar vacío.", usuario);
        if (!usuario.matches("[a-zA-Z]+"))
            propagarUsuarioNoValido("El usuario debe contener solo carácteres alfabéticos. (ejemplo -> masenper)", usuario);
        if (usuario.length() < 4 || usuario.length() > 10)
            propagarUsuarioNoValido("El usuario no puede tener menos de tres o más de diez carácteres.", usuario);
    }
}
