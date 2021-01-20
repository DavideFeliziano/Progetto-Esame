# Progetto-Esame Meteorite 
## MeteoRite:
Meteorite è un programma che permette all’utente di avere, tramite ricerca per città, previsioni meteorologiche riguardanti
esclusivamente la temperatura: massima, minima e percepita.


## Descrizione:
Il nostro progetto si articola principalmente su  due macro funzioni: 
-la prima è di fornire all’utente finale previsioni sulla temperatura della città ricercata fornendo previsioni a intervalli 
di tre ore riguardanti la temperatura attuale, la massima, la minima e la temperatura percepita e di filtrare la ricerca in base 
alla precisione della previsione tutto in gradi celsius.
-la seconda invece immagazzina le previsioni di sei città Ancona, Buggiano, Castelfidardo, Helsinki,
Lucca e Macerata. Per calcolare l’attendibilità delle previsioni così da dare all’utente finale la possibilità di decidere 
la percentuale di errore nelle previsioni. Questa proprietà confronta i dati attuali con le previsioni del giorno prima per cinque gioni.

I dati meteorologici vengono  reperiti da OpenWeatherMap tramite APIà



## Installazione: 
Meteorite è installabile tramite prompt dei comandi 
```
git clone https://github.com/DavideFeliziano/Progetto-Esame
```


<a name="config"></a>
## Funzionamento:
L’utente deve avviare il programma come applicazione SpringBoot e usare Postman o simili come interfaccia.
Le richieste devono essere effettuate all’indirizzo: localhost:8080

I comandi che l’utente può usare sono:

## TEST
![alt text](https://github.com/DavideFeliziano/Progetto-Esame/blob/main/esame.jpg?raw=true)
