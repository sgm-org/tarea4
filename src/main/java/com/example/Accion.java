package com.example;

import java.util.Objects;

public class Accion {
    private int botOrigen;
    private int botDestino;
    private int cantidad;


    public Accion() {
    }

    public Accion(int botOrigen, int botDestino, int cantidad) {
        this.botOrigen = botOrigen;
        this.botDestino = botDestino;
        this.cantidad = cantidad;
    }

    public int getBotOrigen() {
        return this.botOrigen;
    }

    public void setBotOrigen(int botOrigen) {
        this.botOrigen = botOrigen;
    }

    public int getBotDestino() {
        return this.botDestino;
    }

    public void setBotDestino(int botDestino) {
        this.botDestino = botDestino;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Accion botOrigen(int botOrigen) {
        setBotOrigen(botOrigen);
        return this;
    }

    public Accion botDestino(int botDestino) {
        setBotDestino(botDestino);
        return this;
    }

    public Accion cantidad(int cantidad) {
        setCantidad(cantidad);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Accion)) {
            return false;
        }
        Accion accion = (Accion) o;
        return botOrigen == accion.botOrigen && botDestino == accion.botDestino && cantidad == accion.cantidad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(botOrigen, botDestino, cantidad);
    }

    @Override
    public String toString() {
        return "(" +
             getBotOrigen() + ", " +
             getBotDestino() + ", " +
             getCantidad() + ")";
    }

}
