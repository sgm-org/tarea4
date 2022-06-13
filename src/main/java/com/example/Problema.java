package com.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

public class Problema {

    private String id;
    private int bottleSize;
    private Estado initState;

    public Problema() {
    }

    // generamos un problema a partir del fichero y el estado
    public Problema(String ruta)
            throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
        this.initState = leerProblema(ruta);
        this.bottleSize = this.initState.getBottleSize();
    }

    public Problema(String id, int bottleSize, Estado initState) {
        this.id = id;
        this.bottleSize = bottleSize;
        this.initState = initState;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBottleSize() {
        return this.bottleSize;
    }

    public void setBottleSize(int bottleSize) {
        this.bottleSize = bottleSize;
    }

    public Estado getInitState() {
        return this.initState;
    }

    public void setInitState(Estado initState) {
        this.initState = initState;
    }

    public Problema id(String id) {
        setId(id);
        return this;
    }

    public Problema bottleSize(int bottleSize) {
        setBottleSize(bottleSize);
        return this;
    }

    public Problema initState(Estado initState) {
        setInitState(initState);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Problema)) {
            return false;
        }
        Problema problema = (Problema) o;
        return Objects.equals(id, problema.id) && bottleSize == problema.bottleSize
                && Objects.equals(initState, problema.initState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bottleSize, initState);
    }

    @Override
    public String toString() {
        String st = "";

        st = "{" + '\n' +
                "id:  " + id + '\n' +
                "bottleSize:  " + bottleSize + '\n' +

                "initState:  " + initState.getStringEstadoCorrecto() +
                '\n' + "}";

        return st;
    }

    public Estado leerProblema(String ruta)
            throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
        List<Bottle> botellas = new ArrayList<Bottle>();
        JSONParser parser = new JSONParser();
        Object o = parser.parse(new FileReader(ruta));
        JSONObject jsonObject = (JSONObject) o;

        String id = (String) jsonObject.get("id");
        this.id = id;
        long bottleSizelong = (long) jsonObject.get("bottleSize"); // es long porque java lang acepta long y no int

        this.bottleSize = (int) bottleSizelong;
        JSONArray jsonArray = (JSONArray) jsonObject.get("initState");

        for (int i = 0; i < jsonArray.size(); i++) {
            Liquid l = new Liquid();
            Bottle b = new Bottle();
            JSONArray contenidoBotella = (JSONArray) jsonArray.get(i);

            for (int j = contenidoBotella.size() - 1; j >= 0; j--) {

                JSONArray liquidoBotella = (JSONArray) contenidoBotella.get(j);
                long[] tuplaLiquido = new long[2];

                for (int k = 0; k < liquidoBotella.size(); k++) {
                    Object[] li = liquidoBotella.toArray();
                    tuplaLiquido[k] = (long) li[k];

                }
                l = new Liquid((int) tuplaLiquido[0], (int) tuplaLiquido[1]);
                b.añadirLiquido(l);

            }
            botellas.add(b);
        }
        Estado e = new Estado(botellas, bottleSize);
        this.initState = e;
        return e;

    }

    public boolean esObjetivo(Estado estado) {
        boolean esObjetivo = true;
        int cont = 0;
        for (int i = 0; i < estado.getEstado().size(); i++) {
            // si la botella no esta vacia
            if (!estado.getEstado().get(i).getLiquido().isEmpty()) {
                // si la botella es del mismo color
                if (estado.getEstado().get(i).getLiquido().size() == 1
                        && estado.getEstado().get(i).getLiquido().peek().getCant() == bottleSize) {

                    cont++;
                }
            } else if(estado.getEstado().get(i).isEmpty()) { // si la botella esta vacia
                cont++;
            }

        }
        if (cont == estado.getEstado().size()) {
            return esObjetivo;
        } else {
        esObjetivo = false;

        }
        return esObjetivo;
    }


    public List<Sucesor> getSucesores(Estado estado) throws CloneNotSupportedException {
        Sucesor s = null;

        List<Sucesor> sucesores = new ArrayList<>();
        int coste = 1;
        // Estado e = null;

        List<Bottle> botellas = estado.getEstado();

        for (int i = 0; i < botellas.size(); i++) {
            Bottle b = botellas.get(i);
            Stack<Liquid> pilaOrigen = b.getLiquido();
            /*
             * int cantidad = pilaOrigen.size();
             * int sizeBotellas = botellas.size();
             */
            for (int j = 0; j < botellas.size(); j++) {

                if (!pilaOrigen.isEmpty()) {
                    Bottle d = botellas.get(j);
                    // System.out.println(botellas.indexOf(d));
                    int cant = b.getCantDeArriba();
                    if (i != j) {
                        if (estado.ES_AccionPosible(b, d, cant) == true) {
                            Estado estadoNuevo = null;
                            estadoNuevo = new Estado(estado.Accion(b, d, cant));
                            Accion a = new Accion(i, j, cant);
                            s = new Sucesor(a, estadoNuevo, coste);
                            estadoNuevo = null;
                            a = null;
                            sucesores.add(s);

                        }
                    }
                }
            }
        }

        return sucesores;
    }

}
