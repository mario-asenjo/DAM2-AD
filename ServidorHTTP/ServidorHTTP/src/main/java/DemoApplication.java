
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Anotación que indica que es una aplicación Spring Boot
@RestController  // Indica que esta clase maneja peticiones HTTP
@RequestMapping("/api")  // Prefijo de la ruta para los endpoints de esta API
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args); // Inicia la aplicación Spring Boot
    }

    /**
     * Endpoint que devuelve una lista de personas extraídas de un archivo XML.
     * @return Lista de personas en formato JSON (List<Map<String, String>>)
     */
    @GetMapping("/personas")
    public List<Map<String, String>> getPersonas() {
        List<Map<String, String>> personas = new ArrayList<>();
        /**
         *
         * */
        try {
            // Cargar el archivo XML desde los recursos del proyecto
            File file = new ClassPathResource("personas.xml").getFile();

            // Crear un parser de XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            // Obtener todos los nodos "persona"
            NodeList nodeList = doc.getElementsByTagName("persona");

            // Iterar sobre cada nodo "persona" y extraer los datos
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Map<String, String> persona = new HashMap<>();
                    persona.put("id", element.getElementsByTagName("id").item(0).getTextContent());
                    persona.put("nombre", element.getElementsByTagName("nombre").item(0).getTextContent());
                    persona.put("edad", element.getElementsByTagName("edad").item(0).getTextContent());
                    personas.add(persona);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // En producción se recomienda usar logs en lugar de imprimir errores
        }

        return personas; // Se devuelve la lista de personas en formato JSON
    }
}
