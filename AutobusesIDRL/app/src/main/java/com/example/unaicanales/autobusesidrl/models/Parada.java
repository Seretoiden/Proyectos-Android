package com.example.unaicanales.autobusesidrl.models;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class Parada {

    private String nombre;
    private float latitud;
    private GeoPoint latLong;
    private List<Autobus> lineas;

    public Parada(){

    }

    public Parada(String nombre, float latitud, GeoPoint longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.latLong = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public GeoPoint getLatLong() {
        return latLong;
    }

    public void setLatLong(GeoPoint latLong) {
        this.latLong = latLong;
    }

    public List<Autobus> getLineas() {
        return lineas;
    }

    public void setLineas(List<Autobus> lineas) {
        this.lineas = lineas;
    }
}
