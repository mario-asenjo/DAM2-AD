package com.masenjo.model;
import javax.persistence.*;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "puntuacion", nullable = false)
    private int puntuacion;

    @ManyToOne
    @JoinColumn(name = "videojuego_id")
    private Videojuego videojuego;

    // Constructor vacío (obligatorio para Hibernate)
    public Jugador() {
    }

    // Constructor con parámetros básicos
    public Jugador(String nombre, int puntuacion, Videojuego videojuego) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.videojuego = videojuego;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", puntuacion=" + puntuacion +
                ", videojuego=" + videojuego +
                '}';
    }
}
