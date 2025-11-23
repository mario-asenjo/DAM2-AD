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

    @Override
    public String toString() {
        return String.format("ID -> [%d] - Entrenador : %s - Pueblo -> %s\n\t", this.id, this.nombre, this.pueblo);
    }
}
