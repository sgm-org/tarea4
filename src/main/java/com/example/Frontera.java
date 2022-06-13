package com.example;

import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class Frontera {

	private PriorityQueue<Nodo> frontera;


	public Frontera() {
		this.frontera = new PriorityQueue<Nodo>();
	}

	public Frontera(PriorityQueue<Nodo> frontera) {
		this.frontera = frontera;
	}

	public PriorityQueue<Nodo> getFrontera() {
		return this.frontera;
	}

	public void setFrontera(PriorityQueue<Nodo> frontera) {
		this.frontera = frontera;
	}

	public Frontera frontera(PriorityQueue<Nodo> frontera) {
		setFrontera(frontera);
		return this;
	}
/* 
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Frontera)) {
			return false;
		}
		Frontera frontera = (Frontera) o;
		return Objects.equals(frontera, frontera.frontera);
	} */
	//CLEAR FRONTERA
	public void clear() {
		this.frontera.clear();
	}
	//ADD
	public void add(Nodo nodo) {
		this.frontera.add(nodo);
	}
	//REMOVE
	public Nodo remove() {
		return this.frontera.remove();
	}
	//IS EMPTY
	public boolean isEmpty() {
		return this.frontera.isEmpty();
	}


	


}
