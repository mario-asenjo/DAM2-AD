package controlador;

import excepciones.LecturaEscrituraException;
import excepciones.StringNoValidoException;
import modelo.ListaPreguntas;
import modelo.Pregunta;
import servicio.ServicioBD;
import servicio.ServicioFichero;
import vista.Colores;
import vista.Consola;
import vista.Escaner;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ControladorPreguntas {
    public static int mostrarRespuesta(boolean modoExamen, boolean esCorrecta) {
        if (!modoExamen && !esCorrecta) {
            Consola.mostrarFraseEndl("KO.");
            if (Escaner.pedirBoolean("Quieres repetir la pregunta?: ")) {
                return (-1);
            }
        } else if (!modoExamen) {
            Consola.mostrarFraseEndl("OK.");
        }
        return (0);
    }

    public static void mostrarPorcentajeAciertos(Map<Pregunta, Boolean> resultados) {
        double total = resultados.size();
        double aciertos;
        int porcentaje;
        Colores color;

        aciertos = 0;
        for (Map.Entry<Pregunta, Boolean> entry : resultados.entrySet()) {
            if (entry.getValue()) {
                aciertos++;
            }
        }
        porcentaje = (int)(aciertos / total * 100);
        Consola.mostrarFrase(String.format("Aciertos: %d sobre Total Preguntas: %d --> ",(int)aciertos, (int)total), Colores.AMARILLO);
        color = porcentaje >= 50 ? Colores.VERDE : Colores.ROJO;
        Consola.mostrarFraseSinInicio(String.format("(%d%%)", porcentaje), color);
    }

    public static void mostrarResultados(Map<Pregunta, Boolean> resultados, boolean modoExamen) {
        if (modoExamen) {
            Consola.mostrarFraseEndl("### RESULTADOS ###", Colores.AMARILLO);
            for (Map.Entry<Pregunta, Boolean> entry : resultados.entrySet()) {
                Consola.mostrarFraseEndl(entry.getKey().getEnunciado(), Colores.AMARILLO);
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
        ServicioFichero servicioFichero;
        ListaPreguntas listaPreguntas;
        ServicioBD servicioBD;
        int numeroPreguntas;
        boolean modoExamen;
        Map<Pregunta, Boolean> resultados;
        char opcion;
        boolean esCorrecta;

        servicioFichero = new ServicioFichero("Data/50preguntasKotlin.txt");
        try {
            listaPreguntas = new ListaPreguntas(servicioFichero.leer());
            servicioBD = new ServicioBD();
            servicioBD.eliminarContenidoSiHubiese();
            servicioBD.insertarPreguntas(listaPreguntas.obtenerTodas());

            numeroPreguntas = Escaner.pedirEntero("Introduce un número de preguntas: ");
            listaPreguntas.seleccionarPreguntas(numeroPreguntas);
            modoExamen = Escaner.pedirBoolean("MODO EXAMEN (SI O NO): ");
            resultados = new HashMap<>();
            for (int i = 0; i < listaPreguntas.obtenerSeleccionadas().size(); i++) {
                Consola.mostrarFraseEndl(listaPreguntas.obtenerSeleccionadas().get(i).toString());
                opcion = Escaner.pedirChar("Introduce una opcion: ");
                esCorrecta = servicioBD.esOpcionCorrecta(opcion, listaPreguntas.obtenerSeleccionadas().get(i).getId());
                resultados.put(listaPreguntas.obtenerSeleccionadas().get(i), esCorrecta);
                i += mostrarRespuesta(modoExamen, esCorrecta);
            }
            mostrarResultados(resultados, modoExamen);
        } catch (SQLException | StringNoValidoException | LecturaEscrituraException e) {
            Consola.mostrarExcepcion(e);
        }
    }
}
