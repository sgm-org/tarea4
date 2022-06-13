package com.example;

import java.util.Objects;

public class Liquid  {
    private int color;
    private int cant;

    public Liquid() {
    }

    public Liquid(int color, int cant) {
        this.color = color;
        this.cant = cant;
    }

    public Liquid(Liquid l) {
        this.color = l.color;
        this.cant = l.cant;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCant() {
        return this.cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public Liquid color(int color) {
        setColor(color);
        return this;
    }

    public Liquid cant(int cant) {
        setCant(cant);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Liquid)) {
            return false;
        }
        Liquid liquid = (Liquid) o;
        return color == liquid.color && cant == liquid.cant;
    }
    // clonamos 
    @Override
    protected Object clone() throws CloneNotSupportedException {
    
        return super.clone();
    }



    @Override
    public int hashCode() {
        return Objects.hash(color, cant);
    }

    @Override
    public String toString() {
        return "[" + color+ "," + cant + "]" ;
    }
    
    public Liquid deepCopy() {
    	Liquid copy = new Liquid(this.color, this.cant);
        copy.setColor(copy.getColor());
        copy.setCant(copy.getCant());
        return copy;
    }

    public void modificarCantidad(int cantidad) {
    	this.cant += cantidad;
    }

}
