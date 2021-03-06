package Utilities;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CassaAttrezzi 
{
	/**
	 * Questo metodo serve per troncare il double dato come risultato dalla media. 
	 * E' necessario perchè a quanto pare in questo linguaggio non esiste un modo per decidere quanti decimali hanno i double.
	 * 
	 * Chiamato da mediaTemp() e varianza();
	 * 
	 * 
	 * @param media Double che contiene il valore da arrotondare
	 * @param m Stringa su cui memorizzo il double (in realtà non è poi strettamente necessaria)
	 * @return
	 */
	public String arrotondatore(double media, String m)
	{
		String ret ="";
		m = Double.toString(media);
		
		char[] sparta = new char[4];
		
		for(int i = 0; i<4 && i<m.length(); i++)
		{
			sparta[i] = m.charAt(i);
			ret += sparta[i];
		}
		
		return ret;
	}
	
	/**
	 * Metodo superfluo e spartano, per non dire ridicolo, che prende la prima e l'ultima data del json
	 * (cioè mi dice da quando a quando vanno le previsioni)
	 * 
	 * @param ja vettore da cui prendo le date
	 * @return stringa con le date che ho estrapolato
	 */
	public String maxMinData(JSONArray ja)
	{
		String d = "Da ";
		JSONObject jo = new JSONObject();
		jo = (JSONObject) ja.get(1);
		d += jo.get("data").toString();
		d+=" a ";
		jo = (JSONObject) ja.get(5);
		d +=jo.get("data").toString();
		return d;
	}
	
	/**
	 * Metodo pressochè inutile che calcola il complementare della variazione, cioè la precisione.
	 * 
	 * Usato da calcolaDeviazione() in StatGen;
	 * 
	 * @param var variazione tra due valori
	 * @return precisione corrispondente;
	 */
	public double complementareDeviazione(double var)
	{
		double pre = 100 -var;
		return pre;
	}
	
	/**
	 * Questo metodo carica un array di String.
	 * Credo fermamente che sia in assoluto il modo peggiore per fare questa cosa.
	 * 
	 * @param nomiFile
	 * @return
	 */
	public Vector magnificiSette(Vector nomiFile)
	{
		nomiFile.add("Ancona");
		nomiFile.add("Buggiano");
		nomiFile.add("Castelfidardo");
		nomiFile.add("Helsinki");
		nomiFile.add("Lucca");
		nomiFile.add("Macerata");
		return nomiFile;
	}

}
