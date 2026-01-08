package cliente;

public class Persona {
    private String id;
    private String nombre;
    private String edad;

    // Constructor vac´´io para Jackson
    public Persona() {

    }

    // Constructor
    public Persona(String id, String nombre, String edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    // Getters y toString()
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEdad() { return edad; }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Persona{id='" + id + "', nombre='" + nombre + "', edad='" + edad + "'}";
    }
}

