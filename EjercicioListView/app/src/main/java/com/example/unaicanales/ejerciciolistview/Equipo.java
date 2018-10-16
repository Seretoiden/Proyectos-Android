package com.example.unaicanales.ejerciciolistview;


import android.graphics.drawable.Drawable;


public class Equipo {

    private String nombre;
    private String descripcion;
    private int numSocios;
    private int imagen;

    public Equipo() {
        super();
    }

    public Equipo(String nombre, String descripcion, int numSocios, int imagen) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumSocios() {
        return numSocios;
    }

    public void setNumSocios(int numSocios) {
        this.numSocios = numSocios;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}
