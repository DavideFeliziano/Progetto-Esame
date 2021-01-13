package Filters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import JSONHandler.JSONgest;
import ProgettoEsame.MeteoRite.Model.GestioneFile;
import ProgettoEsame.MeteoRite.Model.Services;
import Utilities.CassaAttrezzi;

public class StatGen 
{
	public void statisticator(String nomeFile)
	{
		JSONArray jaStat = new JSONArray();
		JSONObject jo = new JSONObject();
		
		GestioneFile g  = new GestioneFile();
		JSONgest jg = new JSONgest();
		CassaAttrezzi ca = new CassaAttrezzi();
		Services ser = new Services();
		
		double t = 0.0;
		double tmin = 0.0;
		double tmax = 0.0;
		double varian =0.0;
		String data = "";
		
		String citta = "";
		
		jaStat = g.caricaDaFile(nomeFile);
		
		t = jg.mediaTemp(jaStat, 1);
		tmin = jg.maxMax(jaStat, 1);
		tmax = jg.minMin(jaStat, 1);
		data = ca.maxMinData(jaStat);
		
		jo = (JSONObject) jaStat.get(0);
		citta = jo.get("city").toString();
		
		
		jo = ser.boxer(t, tmin, tmax);
		jo.put("data", data);
		jo.put("city", citta);
		
		//manca varianza
		
	}
	
	public double varianza(JSONArray ja, double valoreMedio)
	{
		double var =0.0;
		JSONObject jo = new JSONObject();
		
		
		//media dei quadrati delle differenze, tra i valori Xi e la media
		
		double media  = 0.0;
		double scarto = 0.0;
		double app = 0.0;
		
		int i = 0;
		for(i = 1; i< ja.size(); i++)
		{
			jo = (JSONObject) ja.get(i);
			app = (double) jo.get("temp");
			scarto = app-valoreMedio;
			scarto = scarto*scarto;
			media+= scarto;
		}
		var= media/(i-1);
		
		
		return var;
	}

}
