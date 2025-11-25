package _3_modelo;

public class Entrenador {
    private final long id;
    private String nombre;
    private String pueblo;
    private int edad;
    private Pokedex pokedex;

    public Entrenador(long id, String nombre, String pueblo, int edad, Pokedex pokedex) {
        this.id = id;
        this.nombre = nombre;
        this.pueblo = pueblo;
        this.edad = edad;
        this.pokedex = pokedex;
    }

    public Entrenador(long id, String nombre, String pueblo, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.pueblo = pueblo;
        this.edad = edad;
    }

    public long getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getPueblo() {
        return this.pueblo;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPueblo(String pueblo) {
        this.pueblo = pueblo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Pokedex getPokedex() {
        return this.pokedex;
    }

    @Override
    public String toString() {
        return String.format("ENTRENADOR -> ID: %d, Nombre: %s, Edad: %d, Pueblo: %s {\n\t%s}", id, nombre, edad, pueblo, pokedex.toString());
    }
}
