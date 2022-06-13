package com.example;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;
import java.security.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.lang.Cloneable;
import java.math.BigInteger;

public class Estado implements Serializable, JsonUtil {

    private List<Bottle> estado;
    private int bottleSize;

    public Estado() {
    }

    public Estado(List<Bottle> estado, int bottleSize) {
        this.estado = estado;
        this.bottleSize = bottleSize;
    }

    public Estado(Estado accion) {
        this.estado = accion.getEstado();
        this.bottleSize = accion.getBottleSize();
    }

    public Estado(List<Bottle> listaBotellas) {
        this.estado = listaBotellas;
    }

    public List<Bottle> getEstado() {
        return estado;
    }

    public void setEstado(List<Bottle> estado) {
        this.estado = estado;
    }

    public int getBottleSize() {
        return this.bottleSize;
    }

    public void setBottleSize(int bottleSize) {
        this.bottleSize = bottleSize;
    }

    public Estado estado(List<Bottle> estado) {
        setEstado(estado);
        return this;
    }

    public Estado bottleSize(int bottleSize) {
        setBottleSize(bottleSize);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Estado)) {
            return false;
        }
        Estado estado = (Estado) o;
        return Objects.equals(estado, estado.estado) && Objects.equals(bottleSize, estado.bottleSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estado, bottleSize);
    }

    @Override
    public String toString() {
        String estadoCorrecto = null;

        estadoCorrecto = getStringEstadoCorrecto();

        return estadoCorrecto;

    }

    public int indexOfBottle(Bottle bottle) {
        return estado.indexOf(bottle);

    }

    public Estado deepCopy() {
        List<Bottle> listaBotellas = new ArrayList<Bottle>();
        Bottle auxiliar = null;
        for (int i = 0; i < this.estado.size(); i++) {
            auxiliar = this.estado.get(i);
            listaBotellas.add(auxiliar.deepCopy());

        }
        Estado copiado = new Estado(listaBotellas);
        copiado.setBottleSize(bottleSize);
        return copiado;
    }
    // copiamos objeto sin que se cambie el original

    // clonamos objeto
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Estado cloned = (Estado) super.clone();
        cloned.estado = new ArrayList<Bottle>();
        for (Bottle bottle : this.estado) {
            cloned.estado.add((Bottle) bottle.clone());
        }
        return cloned;
    }

    public boolean ES_AccionPosible(Bottle botOrigen, Bottle botDestino, int cantidad) {
        boolean ES_AccionPosible = false;
        if (!botOrigen.getLiquido().isEmpty()) {
            if (!botDestino.getLiquido().isEmpty()) {
                if ((botDestino.getCantidadliquido() + cantidad) <= this.bottleSize
                        && botDestino.getLiquido().peek().getColor() == botOrigen.getLiquido().peek().getColor()) {
                    ES_AccionPosible = true;
                }
            } else {
                ES_AccionPosible = true;
            }
        }

        return ES_AccionPosible;
    }

    public Estado Accion(Bottle botOrigen, Bottle botDestino, int cantidad) throws CloneNotSupportedException {

        int botOrigenIndex = this.indexOfBottle(botOrigen);
        int botDestinoIndex = this.indexOfBottle(botDestino);

        // creamos una copia del estado
        Estado estadoCopia = this.deepCopy();
        if (ES_AccionPosible(botOrigen, botDestino, cantidad)) {
            if (estadoCopia.getEstado().get(botDestinoIndex).getLiquido().isEmpty()) {
                estadoCopia.getEstado().get(botDestinoIndex).getLiquido().push(estadoCopia.getEstado().get(botOrigenIndex).getLiquido().pop());
            } else {
                Liquid l = estadoCopia.getEstado().get(botOrigenIndex).getLiquido().pop();
                int cant = l.getCant();
                estadoCopia.getEstado().get(botDestinoIndex).getLiquido().peek().modificarCantidad(cant);
            }
        }
        return estadoCopia;
    }

    public String getMD5(String jsonString) {
        String md5 = "";
        try {
            String hash = JsonUtil.getHashMD5(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    public String getStringEstadoCorrecto() {
        String stringEstadoCorrecto = "[";

        List<Bottle> botellas = this.estado;

        for (int b = 0; b < botellas.size(); b++) {
            if (b != botellas.size() - 1) {
                stringEstadoCorrecto += botellas.get(b).getStringBottle() + ",";
            } else {
                stringEstadoCorrecto += botellas.get(b).getStringBottle();
            }

        }
        stringEstadoCorrecto += "]";
        return stringEstadoCorrecto;
    }

    public String md5() {
        String md5 = JsonUtil.getHashMD5(getStringEstadoCorrecto());
        return md5;
    }

    public void imprimir() {
        Iterator<Bottle> it = estado.iterator();
        while (it.hasNext()) {
           System.out.print(it.next().getStringBottle());
        }
    }
}
