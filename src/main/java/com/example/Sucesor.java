package com.example;


import java.util.Objects;


public class Sucesor {
    
    private Accion accion;
    private Estado estado;
    private double costo;



    public Sucesor() {
    }

    public Sucesor(Accion accion, Estado estado, double costo) {
        this.accion = accion;
        this.estado = estado;
        this.costo = costo;
    }

    public Accion getAccion() {
        return this.accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Sucesor accion(Accion accion) {
        setAccion(accion);
        return this;
    }

    public Sucesor estado(Estado estado) {
        setEstado(estado);
        return this;
    }

    public Sucesor costo(double costo) {
        setCosto(costo);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Sucesor)) {
            return false;
        }
        Sucesor sucesor = (Sucesor) o;
        return Objects.equals(accion, sucesor.accion) && Objects.equals(estado, sucesor.estado) && costo == sucesor.costo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accion, estado, costo);
    }

    @Override
    public String toString() {
        return "{" +
            " accion='" + getAccion() + "'" +
            ", estado='" + getEstado().toString() + "'" +
            ", costo='" + getCosto() + "'" +
            "}";
    }

    public void imprimir() {
        System.out.print("\n"+ accion.toString()+"\n");
        estado.imprimir();
    }

}