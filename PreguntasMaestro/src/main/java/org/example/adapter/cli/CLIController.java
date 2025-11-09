package org.example.adapter.cli;

import org.example.adapter.cli.view.Colores;
import org.example.adapter.cli.view.Consola;
import org.example.adapter.cli.view.Escaner;
import org.example.app.ComprobarRespuestaService;
import org.example.app.ImportarPreguntasService;
import org.example.app.ObtenerPreguntasAleatoriasService;
import org.example.domain.model.Pregunta;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CLIController {
    private final ImportarPreguntasService importarSvc;
    private final ObtenerPreguntasAleatoriasService randomSvc;
    private final ComprobarRespuestaService checkSvc;

    public CLIController(ImportarPreguntasService ips, ObtenerPreguntasAleatoriasService opas, ComprobarRespuestaService crs) {
        this.importarSvc = ips;
        this.randomSvc = opas;
        this.checkSvc = crs;
    }

    public void iniciar() {
        int numPreguntas = -1;
        boolean modoExamen = false;
        List<Pregunta> preguntas;
        Map<String, Boolean> resultados = new LinkedHashMap<>();
        char opcion;
        boolean esCorrecta;

        try {

            /**
             * Examen?
             */
            modoExamen = Escaner.pedirBoolean("¿MODO EXAMEN?: ");
            numPreguntas = Escaner.pedirEntero("Introduce número de preguntas: ");

            /**
             * Cargamos N preguntas desde la bd.
             */
            preguntas = randomSvc.obtener(numPreguntas);

            Consola.mostrarFraseEndl("COMENZAMOS EL TEST");
            for (int i = 0; i < preguntas.size(); i++) {
                Consola.mostrarFraseEndl(preguntas.get(i).toString());
                opcion = Escaner.pedirChar("Introduce una opcion: ");
                esCorrecta = checkSvc.esCorrecta(preguntas.get(i).getId(), opcion);
                resultados.put(preguntas.get(i).getEnunciado(), esCorrecta);
                i += mostrarRespuesta(modoExamen, esCorrecta);
            }
            mostrarResultados(resultados, modoExamen);
        } catch (SQLException e) {
            Consola.mostrarExcepcion(e);
        }
    }

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

}
