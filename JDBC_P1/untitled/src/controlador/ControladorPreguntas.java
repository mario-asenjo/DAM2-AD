package controlador;

import excepciones.LecturaEscrituraException;
import excepciones.NumeroNoValidoException;
import excepciones.StringNoValidoException;
import modelo.ListaPreguntas;
import servicio.ServicioBDPreguntas;
import servicio.ServicioFicheroPreguntas;
import vista.Colores;
import vista.Consola;
import vista.Escaner;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ControladorPreguntas {
    /**
     * Muestra el resultado dependiendo de si estamos en modo
     * examen o no, ya que los resultados se guardan fuera y se
     * recibe para cada pregunta hecha, si ha sido correcta la
     * respuesta del usuario.
     * @param modoExamen
     * @param esCorrecta
     * @return Estado de la i en bucle del controlador, permitiendo
     * repetir pregunta.
     */
    public static int mostrarRespuesta(boolean modoExamen, boolean esCorrecta) {
        if (!modoExamen && !esCorrecta) {
            Consola.mostrarFraseEndl("KO.",Colores.ROJO);
            if (Escaner.pedirBoolean("Quieres repetir la pregunta?: ")) {
                return (-1);
            }
        } else if (!modoExamen) {
            Consola.mostrarFraseEndl("OK.", Colores.VERDE);
        }
        return (0);
    }

    /**
     * Muestra los resultados en forma de porcentaje, de
     * los aciertos sobre el total de preguntas hechas al usuario.
     * @param resultados
     */
    public static void mostrarPorcentajeAciertos(Map<String, Boolean> resultados) {
        double total = resultados.size();
        double aciertos;
        int porcentaje;
        Colores color;

        aciertos = 0;
        for (Map.Entry<String, Boolean> entry : resultados.entrySet()) {
            if (entry.getValue()) {
                aciertos++;
            }
        }
        porcentaje = (int)(aciertos / total * 100);
        Consola.mostrarFrase(String.format("Aciertos: %d sobre Total Preguntas: %d --> ",(int)aciertos, (int)total), Colores.AMARILLO);
        color = porcentaje >= 50 ? Colores.VERDE : Colores.ROJO;
        Consola.mostrarFraseSinInicio(String.format("(%d%%)", porcentaje), color);
    }

    /**
     * Si no estamos en modo examen se han ido imprimiendo los resultados
     * para cada intento hecho por el usuario y no hace falta hacerlo de
     * nuevo.
     * @param resultados
     * @param modoExamen
     * Si estamos en modo examen, hasta este punto no se han mostrado
     * los resultados para cada pregunta preguntada al usuario, por
     * lo que debemos mostrar el enunciado de cada pregunta seguido de
     * si ha acertado o no dicha pregunta.
     * En ambos casos se acaba mostrando un procentaje de aciertos sobre el total.
     */
    public static void mostrarResultados(Map<String, Boolean> resultados, boolean modoExamen) {
        if (modoExamen) {
            Consola.mostrarFraseEndl("### RESULTADOS ###", Colores.AMARILLO);
            for (Map.Entry<String, Boolean> entry : resultados.entrySet()) {
                Consola.mostrarFraseEndl(entry.getKey(), Colores.AMARILLO);
                if (entry.getValue()) {
                    Consola.mostrarFraseEndl("OK", Colores.VERDE);
                } else {
                    Consola.mostrarFraseEndl("KO", Colores.ROJO);
                }
            }
        }
        mostrarPorcentajeAciertos(resultados);
    }

    public static void iniciar() {
        ServicioFicheroPreguntas servicioFichero;
        ListaPreguntas listaPreguntas;
        ServicioBDPreguntas servicioBD;
        String numeroPreguntas;
        int registrosCargadosEnLaBD;
        int opcionesCargadasEnLaBD;
        int preguntasCargadasEnLaBD;
        boolean modoExamen;
        Map<String, Boolean> resultados;
        char opcion;
        boolean esCorrecta;

        servicioFichero = new ServicioFicheroPreguntas("Data/50preguntasInformatica.txt");
        listaPreguntas = new ListaPreguntas();
        try {
            /**
             * Leemos el fichero, cargamos el contenido en ListaPreguntas
             * Cargamos en la BBDD las preguntas leidas del fichero.
             */
            listaPreguntas.setListaDePreguntas(servicioFichero.leer());
            servicioBD = new ServicioBDPreguntas();
            servicioBD.eliminarContenidoSiHubiese();
            registrosCargadosEnLaBD = servicioBD.insertarPreguntas(listaPreguntas.getListaDePreguntas());
            preguntasCargadasEnLaBD = registrosCargadosEnLaBD / 5;
            opcionesCargadasEnLaBD = preguntasCargadasEnLaBD * 4;
            Consola.mostrarFraseEndl(String.format("Se han cargado %d registros en la BBDD.\n\t%d Preguntas y %d Opciones\n", registrosCargadosEnLaBD, preguntasCargadasEnLaBD, opcionesCargadasEnLaBD), Colores.VERDE);

            /**
             * Pedimos al usuario un número de preguntas a devolver de la
             * BBDD en orden aleatorio y las guardamos en nuestra ListaPreguntas.
             */
            numeroPreguntas = Escaner.pedirString("Introduce un número de preguntas: ");
            Validaciones.validarEntero(numeroPreguntas, "^(?:[1-9]|[1-9][0-9]{1,2}|1000)$");
            listaPreguntas.setListaDePreguntas(servicioBD.devolverSeleccionPreguntas(Integer.parseInt(numeroPreguntas)));

            /**
             * Preguntamos al usuario si está ejecutando en modo examen o
             * no, permitiendo también el modo practica.
             * Por cada pregunta vamos a mostrar el enunciado y sus opciones
             * y pedimos al usuario su intento.
             * Comprobamos contra la BBDD el intento, guardamos los resultados
             * y dependiendo de si modo examen o no, mostramos la respuesta
             * y damos la opción a repetir la pregunta fallada, o simplemente
             * seguimos hasta el final, mostrando unos resultados generales
             * al terminar el examen.
             */
            modoExamen = Escaner.pedirBoolean("MODO EXAMEN (SI O NO): ");
            resultados = new HashMap<>();
            for (int i = 0; i < listaPreguntas.getListaDePreguntas().size(); i++) {
                Consola.mostrarFraseEndl(listaPreguntas.getListaDePreguntas().get(i).toString());
                opcion = Escaner.pedirChar("Introduce una opcion: ");
                Validaciones.validarString(String.valueOf(opcion), 1, 1, "^[a-dA-D]$");
                esCorrecta = servicioBD.esOpcionCorrecta(opcion, listaPreguntas.getListaDePreguntas().get(i).getId());
                resultados.put(listaPreguntas.getListaDePreguntas().get(i).getEnunciado(), esCorrecta);
                i += mostrarRespuesta(modoExamen, esCorrecta);
            }
            mostrarResultados(resultados, modoExamen);
            servicioBD.cerrarServicio();
        } catch (SQLException | StringNoValidoException | NumeroNoValidoException | LecturaEscrituraException e) {
            Consola.mostrarExcepcion(e);
        }
    }
}
