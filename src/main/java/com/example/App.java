package com.example;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
import com.google.gson.*;

public class App {

	public static void menu() throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException, CloneNotSupportedException{
		final Scanner sc = new Scanner(System.in);
		boolean salir = false;
		int opcion = -1;

		while (salir == false) {
			System.out.println("\nIntroduce número para escoger una opcion y pulse Enter\n "
					+ " 1.- Leer un problema.json (Por defecto p0.json). \n"
					+ " 2.- Escribir un Problema. \n" + "0.- Salir del programa \n");
			opcion = sc.nextInt();

			switch (opcion) {
				case 1:

					/*
					 * Problema problema = new Problema();
					 * //problema = JsonUtil.leerProblema("src/main/java/com/example/p0.json");
					 * Estado E= problema.leerProblema("src/main/java/com/example/p0.json");
					 * List<Bottle> listaBotellas = E.getEstado();
					 * int bottleSize = problema.getBottleSize();
					 */

					Problema problema = new Problema("src/main/java/com/example/p0.json");

					Estado e = new Estado(problema.getInitState().getEstado(), problema.getBottleSize());
					e.toString();
					String stringEstado = e.getStringEstadoCorrecto();
					//Estado eCopia = (Estado) e.deepCopy();
					//tring stringEstadoCorrecto = eCopia.getStringEstadoCorrecto();
					// pasamos la lista de botellas a string
					System.out.println("hash del estado correcto:" + JsonUtil.getHashMD5(stringEstado));
					//List<Sucesor>sucesores = problema.getSucesores(problema.getInitState());
					//mostramos sucesores
					/* for (Sucesor s : sucesores) {
						s.imprimir();
					}
					 */
					 int estrategia = 0;
					System.out.println("Problema elegido: " + problema.toString());
					do {
						System.out.println("Ahora debe elegir estrategia:\n" +
								"1.- BREADTH \n" + "2.- DEPTH \n" + "3.- UNIFORM \n" + "4.- GREEDY \n" +
								"5.- A \n");
						estrategia = sc.nextInt();

					} while (estrategia < 1 || estrategia > 5);

					String estrategiaString = seleccionarEstrategia(estrategia);
					System.out.println("Estrategia elegida: " + estrategiaString);
					System.out.println("\n");

					// resolvemos problema
					algoritmoBusqueda(problema, estrategiaString, 1000000);
					
 
					break;

			}
		}
	}

    // ALGORITMO DE BUSQUEDA
    public static boolean algoritmoBusqueda(Problema p, String estrategia, int prof) throws CloneNotSupportedException {

        boolean encontrado = false;
        Nodo nActual = null;
        Nodo nodoInicial = new Nodo(0.0, p.getInitState(), null, null, 0, 0, 0);
        nodoInicial.setValor(nodoInicial.calcularValorEstrategia(estrategia, nodoInicial));

        Frontera frontera = new Frontera();
        frontera.clear();
        frontera.add(nodoInicial);

        Map<String, Double> visitados = new HashMap<String, Double>(); // mapa de nodos visitados
        List<Nodo> listaNodosSolucion = new ArrayList<Nodo>();

        while (!frontera.isEmpty() && !encontrado) {
            nActual = frontera.remove();
            if (p.esObjetivo(nActual.getEstado())) {
                encontrado = true;

            } else {
                if (nActual.getProfundidad() < prof && !visitados.containsKey(nActual.getEstado().md5())) {
                    visitados.put(nActual.getEstado().md5(), nActual.getValor());
                        List<Sucesor> sucesores = new ArrayList<Sucesor>();

                    sucesores = p.getSucesores(nActual.getEstado());
                    List<Nodo> listaNodosSucesores = new ArrayList<Nodo>();

                    listaNodosSucesores = generarListaNodos(sucesores, nActual, prof, estrategia);
                    // expandimos frontera
                    for (Nodo n : listaNodosSucesores) {
                        n.setValor(n.calcularValorEstrategia(estrategia, n));
                        frontera.add(n);
                    }
                }
                /*
                 * if (nodoActual.getProfundidad() < prof
                 * && !visitados.containsKey(nodoActual.getEstado().getMD5(jsonString))) {
                 * visitados.put(nodoActual.getEstado().getMD5(jsonString),
                 * nodoActual.getValor());
                 * List<Sucesor> sucesores = p.getSucesores(nodoActual.getEstado());
                 * 
                 * }
                 * }
                 */

            }
            // si encontramos sol
            if (encontrado) {
                while (nActual.getPadre() != null) {
                    listaNodosSolucion.add(nActual);
                    nActual = nActual.getPadre();
                }
                listaNodosSolucion.add(nActual);// este es el nodo inicial
                Collections.reverse(listaNodosSolucion);
                for (Nodo n : listaNodosSolucion) {
                    System.out.println(n.toString());
                }
            }

        }
        return encontrado;

    }
	
    public static List<Nodo> generarListaNodos(List<Sucesor> sucesores, Nodo nodo, int prof, String estrategia) {
        List<Nodo> listaNodos = new ArrayList<Nodo>();
        
        if (nodo.getProfundidad() < prof) {
			Nodo nodoAux = null;
            for (Sucesor s : sucesores) {
                switch (estrategia) {
                    case "BREADTH":
                        nodoAux = new Nodo(nodo.getCostoAcumulado() + s.getCosto(), s.getEstado(), nodo, s.getAccion(),nodo.getProfundidad() + 1, 0, nodo.getProfundidad() + 1);
                        break;
                    case "DEPTH":
                        nodoAux = new Nodo(nodo.getCostoAcumulado() + s.getCosto(), s.getEstado(), nodo, s.getAccion(),nodo.getProfundidad() + 1, 0, (1.0 / (double) (nodo.getProfundidad() + 1)));
                        break;

                    case "UNIFORM":
                        nodoAux = new Nodo(nodo.getCostoAcumulado() + s.getCosto(), s.getEstado(), nodo, s.getAccion(), nodo.getProfundidad() + 1, 0, nodo.getCostoAcumulado() + s.getCosto());
                        break;
						case "GREEDY":
							nodoAux = new Nodo(nodo.getCostoAcumulado() + s.getCosto(), s.getEstado(), nodo, s.getAccion(), nodo.getProfundidad() + 1, nodo.getHeuristica(),nodo.getHeuristica() );

                        break;
						case "A":
							nodoAux = new Nodo(nodo.getCostoAcumulado() + s.getCosto(), s.getEstado(), nodo, s.getAccion(), nodo.getProfundidad() + 1, nodo.getHeuristica(),nodo.getHeuristica() + s.getCosto()+nodo.getCostoAcumulado() );

						break;

						
		
                }

                listaNodos.add(nodoAux);

            }
        }
        return listaNodos;
    }

	/*
	 * 
	 * 
	 * do {
	 * System.out.println("Ahora debe elegir la estrategia: \n"
	 * + "1.- BREATH \n" + "2.- DEPTH \n" + "3.- UNIFORM \n" + "4.- GREEDY \n" +
	 * "5.- A \n");
	 * estrategia = sc.nextInt();
	 * } while (estrategia < 1 || estrategia > 5);
	 * String estrategiaString = seleccionarEstrategia(estrategia);
	 * 
	 * // ahora procederemos a resolver el problema
	 * 
	 * System.out.println("RESOLVIENDO PROBLEMA...");
	 * List<Nodo> ListaNodosSolucion = new ArrayList<>();
	 * PriorityQueue<Nodo> cola = new PriorityQueue<Nodo>();
	 * Frontera frontera = new Frontera(cola);
	 * ListaNodosSolucion = problema.algoritmsoBusqueda(problema,frontera,
	 * estrategiaString);
	 * //Mostramos la lista de nodos solucion
	 * System.out.println("\nLista de nodos solucion: ");
	 * for (Nodo nodo : ListaNodosSolucion) {
	 * System.out.println(nodo.toString());
	 * }
	 * 
	 * try{
	 * JsonUtil.escribirSolucion(ListaNodosSolucion);
	 * }catch(NullPointerException e1){
	 * System.out.println("No se encontró solución.");
	 * }
	 * 
	 * break;
	 * 
	 * case 2:
	 * // ---------------------------------por
	 * hacer-------------------------------//
	 * break;
	 * case 0:
	 * salir = true;
	 * break;
	 * 
	 * }
	 * 
	 * }
	 * if (salir == true) {
	 * System.out.println("Gracias por usar el programa");
	 * System.exit(0);
	 * }
	 * }
	 * 
	 * public static List<Bottle> seleccionarEstado(List<Estado> estados) {
	 * Scanner sc = new Scanner(System.in);
	 * Estado estado = new Estado();
	 * int numero = -1;
	 * boolean salir = false;
	 * while (salir == false) {
	 * System.out.
	 * println(" ----Para empezar, seleccione un estado de la lista leída anteriormente(número):---- "
	 * );
	 * numero = sc.nextInt();
	 * if (numero >= 0 && numero < estados.size()) {
	 * estado = estados.get(numero);
	 * System.out.println("Estado seleccionado: ");
	 * estado.mostrarEstado();
	 * salir = true;
	 * } else {
	 * System.out.println("El número debe estar entre 0 y " + estados.size() + ".");
	 * }
	 * }
	 * List<Bottle> listaBotellas = estado.getEstado();
	 * return listaBotellas;
	 * 
	 * }
	 */
	public static String seleccionarEstrategia(int estrategia) {
		String estrategiaSeleccionada = "";
		switch (estrategia) {
			case 1:
				estrategiaSeleccionada = "BREADTH";
				break;
			case 2:
				estrategiaSeleccionada = "DEPTH";
				break;
			case 3:
				estrategiaSeleccionada = "UNIFORM";
				break;
			case 4:
				estrategiaSeleccionada = "GREEDY";
				break;
			case 5:
				estrategiaSeleccionada = "A";
				break;
		}
		return estrategiaSeleccionada;
	}

	public static void main(String[] args) throws CloneNotSupportedException, NoSuchAlgorithmException,
			FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {

		menu();

	}

}
