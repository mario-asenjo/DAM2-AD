package org.example.adapter.cli;

import org.example.adapter.cli.view.Colores;
import org.example.adapter.cli.view.Consola;
import org.example.adapter.cli.view.Escaner;
import org.example.config.AppConfig;
import org.example.domain.ports.ExamCatalog;

import java.sql.SQLException;
import java.util.List;

public class CLIMain {
    public static void main(String[] args) {
        int idx;
        String examenElegido;

        AppConfig app = new AppConfig();
        app.start(true);

        List<ExamCatalog.ExamenMeta> examenes = app.listarExamenes();
        if (examenes.isEmpty()) {
            Consola.mostrarFraseEndl("No se han encontrado ficheros de examen en el directorio configurado.", Colores.ROJO);
            System.exit(1);
        }

        Consola.mostrarFraseEndl("#### EXAMENES DISPONIBLES ####", Colores.VERDE);
        for (int i = 0; i < examenes.size(); i++) {
            var e = examenes.get(i);
            Consola.mostrarFraseEndl(String.format("[%d] %s (%,d bytes)", i, e.nombre(), e.bytes()), Colores.AMARILLO);
        }

        while (true) {
            idx = Escaner.pedirEntero("Elige un examen (numero de la lista): ");
            if (idx >= 0 && idx < examenes.size()) break;
            Consola.mostrarFraseEndl("Indice invalido!", Colores.ROJO);
        }

        examenElegido = examenes.get(idx).nombre();

        try {
            var filasAfectadas = app.importarDesde(examenElegido);
            Consola.mostrarFraseEndl(String.format("Importado '%s' -> Preguntas: %d | Opciones: %d", examenElegido, filasAfectadas.preguntasInsertadas(), filasAfectadas.opcionesInsertadas()), Colores.VERDE);
        } catch (Exception e) {
            Consola.mostrarExcepcion(e);
            System.exit(1);
        }

        CLIController cli = new CLIController(app.importarSvc(), app.randomSvc(), app.checkSvc());
        cli.iniciar();
    }
}
