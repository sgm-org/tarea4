package com.example;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Nodo implements Comparable<Nodo> {

    private int id;
    private double costoAcumulado;
    Estado estado;
    Nodo padre;
    Accion accion;
    private int profundidad;
    private double heuristica;
    private double valor;
    private static int contadorId = 0;

    public Nodo() {
    }

    public Nodo(double costoAcumulado, Estado estado, Nodo padre, Accion accion, int profundidad, double heuristica,
            double valor) {
        this.id = contadorId++;
        this.costoAcumulado = costoAcumulado;
        this.estado = estado;
        this.padre = padre;
        this.accion = accion;
        this.profundidad = profundidad;
        this.valor = valor;
        this.heuristica = obtenerValorHeuristico();
    }

    private double obtenerValorHeuristico() {
        double heu = 0;
        ArrayList<Integer> coloresVisitados = new ArrayList<>();
        for (int i = 0; i < estado.getEstado().size(); i++) {
            if (colorHaSidoVisitado(coloresVisitados, estado.getEstado().get(i)) || estado.getEstado().get(i) == null) {
                heu+= estado.getEstado().get(i).size()+1;
            }else{
                heu+= estado.getEstado().get(i).size();
                coloresVisitados.add(estado.getEstado().get(i).getColorDeArriba());
            }
        }
        heu-=estado.getEstado().size();
        return heu;

    }

    private boolean colorHaSidoVisitado(ArrayList<Integer> coloresVisitados, Bottle bottle) {
        if (coloresVisitados.contains(bottle.getColorDeArriba()))
            return true;
        else
            return false;

    }

    

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCostoAcumulado() {
        return this.costoAcumulado;
    }

    public void setCostoAcumulado(double costoAcumulado) {
        this.costoAcumulado = costoAcumulado;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Nodo getPadre() {
        return this.padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public Accion getAccion() {
        return this.accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public double getHeuristica() {
        return this.heuristica;
    }

    public void setHeuristica(float heuristica) {
        this.heuristica = heuristica;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Nodo id(int id) {
        setId(id);
        return this;
    }

    public Nodo costoAcumulado(double costoAcumulado) {
        setCostoAcumulado(costoAcumulado);
        return this;
    }

    public Nodo estado(Estado estado) {
        setEstado(estado);
        return this;
    }

    public Nodo padre(Nodo padre) {
        setPadre(padre);
        return this;
    }

    public Nodo accion(Accion accion) {
        setAccion(accion);
        return this;
    }

    public Nodo profundidad(int profundidad) {
        setProfundidad(profundidad);
        return this;
    }

    public Nodo heuristica(float heuristica) {
        setHeuristica(heuristica);
        return this;
    }

    public Nodo valor(double valor) {
        setValor(valor);
        return this;
    }

    /*
     * @Override
     * public boolean equals(Object o) {
     * if (o == this)
     * return true;
     * if (!(o instanceof Nodo)) {
     * return false;
     * }
     * Nodo nodo = (Nodo) o;
     * return id == nodo.id && costoAcumulado == nodo.costoAcumulado &&
     * Objects.equals(estado, nodo.estado) && Objects.equals(padre, nodo.padre) &&
     * Objects.equals(accion, nodo.accion) && profundidad == nodo.profundidad &&
     * heuristica == nodo.heuristica && valor == nodo.valor;
     * }
     */

    @Override
    public int hashCode() {
        return Objects.hash(id, costoAcumulado, estado, padre, accion, profundidad, heuristica, valor);
    }

    public String getMD5deEstado() throws NullPointerException, CloneNotSupportedException {
        String jsonCorrecto = getEstado().getStringEstadoCorrecto();
        String MD5 = JsonUtil.getHashMD5(jsonCorrecto);
        return MD5;
    }

    /*
     * public String toString() {
     * String result = "";
     * 
     * String jsonString =
     * JsonUtil.deListaDeBotellasToJsonString(getEstado().getEstado());
     * 
     * try {
     * result = "[" +
     * "" + getId() + "]" +
     * "[" + getCostoAcumulado() + ", " +
     * "" + getMD5deEstado() + ", " +
     * "" + getPadre() + ", " +
     * "" + getAccion() + ", " +
     * "" + getProfundidad() + ", " +
     * "" + getHeuristica() + ", " +
     * "" + getValor() + "" +
     * "]";
     * } catch (NullPointerException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * } catch (CloneNotSupportedException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * }
     * 
     * return result;
     */
    // }
    @Override // nos servira para la priority queue de frontera
    public int compareTo(Nodo o) {
        int result = 0;
        if (o.getValor() < getValor()) {
            result = +1;
        } else if (o.getValor() > getValor()) {
            result = -1;
        } else {
            if (o.getId() < getId()) {
                result = +1;
            } else {
                result = -1;
            }
        }
        return result;
    }

    public double calcularValorEstrategia(String estrategia, Nodo nodo) {
        double valor = 0;
        switch (estrategia) {
            case "BREADTH":
                valor = nodo.getProfundidad();
                break;
            case "DEPTH":
                valor = 1 / ((double) (nodo.getProfundidad() + 1));
                break;
            case "UNIFORM":
                valor = nodo.costoAcumulado;
                break;
            case "GREEDY":
                valor = nodo.heuristica;
                break;
            case "A":
                valor = nodo.costoAcumulado + nodo.heuristica;
                break;
        }
        return valor;
    }

    @Override
    public String toString() {
        String result = "";
        DecimalFormat df = new DecimalFormat("#0.00");
        if (getPadre() == null) {

            result = "[" +
                    "" + getId() + "]" +
                    "[" + getCostoAcumulado() + ", " +
                    "" + getEstado().md5() + ", " +
                    "" + "None, " +
                    "" + " None,  " +
                    "" + getProfundidad() + ", " +
                    "" + df.format(Math.abs(getHeuristica())) + ", " +
                    "" + df.format(Math.abs(getValor())) + "" +
                    "]";
        } else {
            result = "[" +
                    "" + getId() + "]" +
                    "[" + getCostoAcumulado() + ", " +
                    "" + getEstado().md5() + ", " +
                    "" + getPadre().getId() + ", " +
                    "" + getAccion() + ", " +
                    "" + getProfundidad() + ", " +
                    "" + df.format(Math.abs(getHeuristica())) + ", " +
                    "" + df.format(Math.abs(getValor())) + "" +
                    "]";
        }

        return result;
    }

    // Objeto comparable para la priorityqueues de los nodos

    // metodos del nodo para el arbol de busqueda

    /*
     * 1. LEER PROBLEMA(YA HECHO SI VES EL MAIN HAY UN METODO QUE LEE UN PROBLEMA)
     *
     * 
     * public void LeerProblema() {
     * Problema problema = new Problema();
     * problema = JsonUtil.leerProblema("src/main/java/com/example/p0.json");
     * List<Bottle> listaBotellas = problema.getInitState().getEstado();
     * Estado e = new Estado(listaBotellas);
     * List<Sucesor> listaSucesores;
     * /*
     * try {
     * listaSucesores = problema.getSucesores(e);
     * for (Sucesor s : listaSucesores) {
     * System.out.println(s.toString());
     * System.out.println("\n\n,\n\n");
     * }
     * } catch (CloneNotSupportedException e1) {
     * // TODO Auto-generated catch block
     * e1.printStackTrace();
     * }
     * 
     * 
     * }
     * 
     * /*
     * 2. Generar Nodo a partir de estado inicial(metodo generarNodoINicial o algo
     * asi)
     *
     * 
     * 
     * public float calcularHeurista() {
     * float h = 0;
     * int coloresVistos[];
     * return h;
     * }
     * 
     * 
     * 
     * }
     */
}