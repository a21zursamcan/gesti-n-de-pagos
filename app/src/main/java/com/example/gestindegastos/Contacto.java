package com.example.gestindegastos;

public class Contacto {
    public String nombre;
    public String ID;
    public int Deuda;

    public Contacto(String nombre, String ID, int deuda) {
        this.nombre = nombre;
        this.ID = ID;
        Deuda = deuda;
    }

    public Contacto(String nombre, String ID) {
        this.nombre = nombre;
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getDeuda() {
        return Deuda;
    }

    public void setDeuda(int deuda) {
        Deuda = deuda;
    }
}
