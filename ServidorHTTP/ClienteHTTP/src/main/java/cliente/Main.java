package cliente;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/personas"))
                .GET()
                .build();

        HttpRequest SQLRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/personasSQL"))
                .header("Content-Type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString("SELECT id, nombre, edad FROM personas")) // SQL INJECTION CUIDADO
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            List<Persona> personas = mapper.readValue(response.body(), new TypeReference<List<Persona>>() {
            });

            personas.forEach(System.out::println);

            HttpResponse<String> SQLResponse = client.send(SQLRequest, HttpResponse.BodyHandlers.ofString());

            String sqlResponse = SQLResponse.body();
            System.out.println(sqlResponse);

        } catch (IOException | InterruptedException | IllegalArgumentException | SecurityException e) {
            System.err.println("No se pudo leer del servidor los datos pedidos.");
        }
    }
}
