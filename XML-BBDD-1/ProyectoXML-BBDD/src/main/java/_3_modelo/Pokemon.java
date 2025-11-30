package _3_modelo;

public class Pokemon {
    private int id;
    private String nombre;
    private String especie;
    private String tipo;
    private int nivel;

    public Pokemon(int id, String nombre, String especie, String tipo, int nivel) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.tipo = tipo;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return String.format("Pokemon -> ID: %d, Nombre: %s, Tipo: %s, Nivel: %d", id, nombre, tipo, nivel);
    }
}
