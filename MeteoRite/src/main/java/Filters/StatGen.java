package Filters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import JSONHandler.JSONgest;
import ProgettoEsame.MeteoRite.Model.GestioneFile;
import ProgettoEsame.MeteoRite.Model.Services;
import Utilities.CassaAttrezzi;

public class StatGen 
{
	/**
	 * Questo metodo legge il file creato da /saveex e usa caricaDaFile per rimpire un arrai
	 * genera quindi le statistiche sulle previsioni lette (cioè, calcola media massimo e minimo della temperatura)
	 * 
	 * Chiama: caricaDaFile, mediaTemp, maxMax, minMin, maxMinData, varianza e boxer();
	 * 
	 * @param nomeFile
	 * @return restituisce un JSONObject con le statistiche del file letto.
	 */
	public JSONObject statisticator(String nomeFile)
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
		String data = "";
		double var = 0.0;
		
		String citta = "";
		
		jaStat = g.caricaDaFile(nomeFile);
		
		t = jg.mediaTemp(jaStat, 1);
		tmin = jg.minMin(jaStat, 1);
		tmax = jg.maxMax(jaStat, 1);
		data = ca.maxMinData(jaStat);
		
		jo = (JSONObject) jaStat.get(0);
		citta = jo.get("city").toString();
		
		jo = ser.boxer(t, tmin, tmax);
		jo.put("data", data);
		jo.put("city", citta);
		var = varianza(jaStat, t);
		jo.put("varianza", var);
		
		return jo;
	}
	
	/**
	 * Questo metodo calcola la varianza
	 * (media dei quadrati delle differenze, tra i valori Xi e la media)
	 * Chiamato da statisticator()
	 * 
	 * @param ja array coi valori
	 * @param valoreMedio media delle temperature calcolata da mediaTemp
	 * @return restituisce double con valore varianza
	 */
	public double varianza(JSONArray ja, double valoreMedio)
	{
		double var =0.0;
		JSONObject jo = new JSONObject();
		CassaAttrezzi cassa = new CassaAttrezzi();
		
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
		String v = Double.toString(var);
		
		v = cassa.arrotondatore(var, v);
		var = Double.valueOf(v);
		
		return var;
	}
	
	/**
	 * Chiama caricaDaFile per leggere leggere le previsioni
	 * Confronta poi queste previsioni con il meteo attuale nel json alla data successiva
	 * 
	 * @param nomeFile nome del Primo file da leggere
	 */
	public void precision(String nomeFile)
	{
		JSONArray ja = new JSONArray();
		JSONArray ja2 = new JSONArray();
		JSONObject jo = new JSONObject();
		JSONObject app = new JSONObject();
		JSONObject var = new JSONObject();
		JSONArray date = new JSONArray();
		String s = "";
		String prossimoFile = "";
		
		GestioneFile g = new GestioneFile();
		CassaAttrezzi ca= new CassaAttrezzi();
		
		ja = g.caricaDaFile(nomeFile);
		//dmm = ca.maxMinData(ja);
		
		//questo for legge le 4 date delle previsioni future
		//comincia da i=2 perchè la prima posizione contine nomeCittà, la seconda le previsioni odierne;
		for(int i = 2; i<ja.size();i++)
		{
			jo = (JSONObject) ja.get(0);
			s = jo.get("data").toString();
			app.put("data", s);
			date.add(app);
		}
		
		//adesso che conosco le date, le uso per cercare i file
		for(int i = 0; i < date.size(); i++)
		{
			s = date.get(i).toString();
			prossimoFile = nomeFile+s;
			
			ja2 = g.caricaDaFile(prossimoFile);//[0] = nomeCitta; [1]= previsioni odierne, il resto è inutile.
			jo = (JSONObject) ja2.get(1); //da ja2 voglio sempre previsioni odierne;
			
			//da ja2 prendo sempre data odierna (posizione 1) ma da ja devo cambiare ogni volta
			app = (JSONObject) ja.get(i+2);//data corrispondente in ja è due posizioni più avanti di date;
			
			//metodo che paragona campi individuali di due jsonobject
			
			var = calcolaDeviazione(jo, app);
			
//qua ce vorebbe na matrice. Salvo la variazione percentuale (complementare precisione) ma dovrei fare media per ogni giorno)
			//posso usare 4 array per salvare valori di ogni giorno (di diverse città)
			//oppure posso provare a caricare variazione trovata per ogni giorno su un array diverso.
			//diverse città caricano stesso array per stessi giorni. alla fine faccio media di questi array
		}
		
		
	}
	
	/**
	 * Calcola la variazione percentuale tra due valori
	 * Chiama variazioneTraValori()
	 * 
	 * Usato da precision() 
	 * 
	 * @param jo Oggetto con il meteo previsto
	 * @param app Oggetto con il meteo effettivo (odierno)
	 * @return Restituisce json con la variazione percentuale per ogni campo del json
	 */
	public JSONObject calcolaDeviazione(JSONObject jo, JSONObject app)
	{
		JSONObject dev = new JSONObject();
		double previsto = 0.0;
		double effettivo = 0.0;
		double var = 0.0;
		
		previsto =(double) app.get("temp");
		effettivo = (double) jo.get("temp");
		var = variazioneTraValori(previsto,effettivo);
		dev.put("temp_var", var);
		
		previsto = (double) app.get("temp_min");
		effettivo = (double) jo.get("temp_min");
		var = variazioneTraValori(previsto,effettivo);
		dev.put("min_var", var);
		
		previsto = (double) app.get("temp_max");
		effettivo = (double) jo.get("temp_max");
		var = variazioneTraValori(previsto,effettivo);
		dev.put("max_var", var);
		
		return dev;
		
	}
	
	/**
	 * Calcola variazione percentuale tra due valori (complementare precisione)
	 * 
	 * Usato da calcolaDeviazione
	 * 
	 * @param previsto
	 * @param effettivo
	 * @return
	 */
	public double variazioneTraValori(double previsto, double effettivo)
	{
		double var = 0.0;
		
		//FORMULA CALCOLO {[(Xf / Xi) x 100] - 100 }%, che diventa {[(Xf - Xi)/ Xi ] x 100} %
		
		var = ((effettivo/previsto)*100) -100;
		var = var*100;
		
		return var;
	}
	

}
