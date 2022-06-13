package com.example;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;



public interface JsonUtil {

	public static String getHashMD5(String jsonS) {
		String md5 = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(jsonS.getBytes(),0,jsonS.length());
			md5 = new BigInteger(1,md.digest()).toString(16);
		} catch (Exception e) {
			return null;

			
			}
		
		return md5;
	}

	/* public static Problema leerProblema(String archivo) {

		Problema problema = new Problema();
		Gson gson = new Gson();
		String linea;

		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			while ((linea = br.readLine()) != null) {
				JsonObject jsonObject = new JsonParser().parse(linea).getAsJsonObject();
				problema.setId(jsonObject.get("id").getAsString());
				problema.setBottleSize(jsonObject.get("bottleSize").getAsInt());
				List<Bottle> listaBotellas = new ArrayList<Bottle>();
				JsonArray jsonArray = jsonObject.get("initState").getAsJsonArray();
				Estado estado = convertJsonToState(jsonArray, problema.getBottleSize());
				problema.setInitState(estado);

			}
			// 1System.out.println(problema.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return problema; */
	/* } */

	/* public static Estado convertJsonToState(JsonArray json, int bottleSize) {
		List<Bottle> listaBotellas = new ArrayList<Bottle>();
		int contBotella = 0;
		for (int i = 0; i < json.size(); i++) { // cada iteracion es una botella
			JsonArray jsonBotella = json.get(i).getAsJsonArray();
			Stack<Liquid> botella = new Stack<Liquid>();
			int cantLiqBot = 0;

			for (int j =jsonBotella.size()-1; j >=0; j--) { // para escribir cada botella
				JsonArray jsonTupla = jsonBotella.get(j).getAsJsonArray();
				int color = jsonTupla.get(0).getAsInt();
				int cant = jsonTupla.get(1).getAsInt();

				Liquid l = new Liquid(color, cant);
				botella.push(l);

				// botella con la tupla al reves ??
			}
			listaBotellas.add(new Bottle(botella));
			contBotella++;
		}
		Estado estado = new Estado(listaBotellas, bottleSize);
		return estado;
	} */
/* 
	public static String deListaDeBotellasToJsonString(List<Bottle> listaBotellas) {

		JsonArray jsonArray = new JsonArray();
		for (Bottle bottle : listaBotellas) {
			JsonArray jsonBotella = new JsonArray();
			for (Liquid liquid : bottle.getLiquido()) {
				JsonArray jsonTupla = new JsonArray();
				jsonTupla.add(liquid.getColor());
				jsonTupla.add(liquid.getCant());
				jsonBotella.add(jsonTupla);
			}
			jsonArray.add(jsonBotella);
		}
		return jsonArray.toString();
	} */

	/* public static Estado jsonArraytoListBottle(JsonArray array) {
		Estado estado = new Estado();
		List<Bottle> botellas = new ArrayList<Bottle>();
		for (int i = 0; i < array.size(); i++) {

		}

		return estado;
	}

	public static JsonArray escribirSolucion(List<Nodo> listaNodosSolucion) {
		JsonArray arraySolucion = new JsonArray();

		return arraySolucion;
	}
 */
	
	
}/*  */
