# Progetto-Esame Meteorite 
## MeteoRite:
MeteoRite è un programma che permette all’utente di avere, tramite ricerca per città, previsioni meteorologiche riguardanti
esclusivamente la temperatura: massima, minima e percepita.


## **Descrizione:**

Il nostro progetto si articola principalmente su  due macro funzioni: 

* la prima è di fornire all’utente finale previsioni sulla temperatura della città ricercata fornendo previsioni a intervalli 
di tre ore riguardanti la temperatura attuale, la massima, la minima e la temperatura percepita e di filtrare la ricerca in base 
alla precisione della previsione tutto in gradi celsius.

* la seconda invece immagazzina le previsioni di sei città Ancona, Buggiano, Castelfidardo, Helsinki,
Lucca e Macerata. Per calcolare l’attendibilità delle previsioni così da dare all’utente finale la possibilità di decidere 
la percentuale di errore nelle previsioni. Questa proprietà confronta i dati attuali con le previsioni del giorno prima per cinque gioni.

I dati meteorologici vengono  reperiti tramite l'API OpenWeatherMap.



## Installazione: 
Meteorite è installabile tramite prompt dei comandi 
```
git clone https://github.com/DavideFeliziano/Progetto-Esame
```

Una volta installato (o importanto il progetto su un IDE) e mandato in esecuzione, il servizio sarà in ascolto e risponderà a chiamate fatte tramite Postman o browser.

## UML:

## Use Case Diagram
![alt text](https://github.com/DavideFeliziano/Progetto-Esame/blob/main/usecasediagram.jpg?raw=true)

Benchè realizzato nelle fasi iniziali del progetto, questo diagramma rappresenta esattamente quelli che sono i casi d'uso di MeteoRite.

L'utente infatti, deve limitarsi ad inserire il nome di una città per visualizzarne le previsioni; inoltre può anche richiedere che le previsioni visualizzate abbiano una certa accuratezza (espressa in percentuale).

Del resto (salvare i dati dell'API per costruire uno storico dal quale poi calcolare le statistiche) si occupa l'amministratore.

## Class Diagram
![alt text](https://github.com/DavideFeliziano/Progetto-Esame/blob/main/esame.jpg?raw=true)

Facendo riferimento allo Use Case Diagram soprariportato, questo Class Diagram è per lo più non veritiero: mancano infatti molte classi e "Servizi" è scritto con due z.

Questo perchè in fase di modellazione, molte criticità (dovute all'API stessa) erano incognite.

Tuttavia lo "scheletro" del progetto è rimasto pressochè quello descritto.


## Sequence Diagram
![alt text](https://github.com/DavideFeliziano/Progetto-Esame/blob/main/secuencediagram.jpg?raw=true)

A differenza degli altri due schemi, il Sequence Diagram è stato realizzato quando una parte del programma era già funzionante.

Pur non andando troppo nello specifico (perchè alcune classi non erano ancora state implementate o comunque hanno cambiato nome successivamente) riesce a rendere l'idea di come procede il programma: usa il nome della città inserito durante la chiamata e restituisce un json preso da OpenWeatherMap.

Già da questo schema si nota come, per gestire l'enorme json fornito dall'API, siano state necessarie molte classi intermedie che "snelliscono" il json, salvando solo le parti necessarie.

## Funzionamento:
L’utente deve avviare il programma come applicazione SpringBoot e usare Postman o simili come interfaccia.

Le richieste devono essere effettuate all’indirizzo: 

**``` localhost:8080 ```**

Il programma provvederà a fare una chiamata tramite all'API OpenWeatherMap.
Durante la richiesta, il programma inserisce in automatico la key per l'API e selezione come unità di misura i gradi Celsius.

NOTA: Tutte le rotte sotto riportate, se non viene inserito il parametro richiesto, effettueranno la chiamata utilizzando un valore di Default.

## ROTTE:

N° | Tipo | Rotta | Descrizione
----- | ------------ | -------------------- | ----------------------
[1](#1) | ` GET ` | `/forecast?citta=` | restituisce un json con le previsioni riguardanti la temperatura della città indicata per i prossimi 5 giorni.
[2](#2) | ` GET ` | `/save?citta=` | salva su un file locale il json risultante dalla chiamata all'API riguardante la città indicata.
[3](#3) | ` GET ` | `/saveex?citta=&?giorno=` | salva un json su un file locale esattamente come /save ma prima di salvarlo ricava dal json dell'API solo i dati essenziali per calcolare poi le statistiche. Inoltre, è presente un secondo parametro (opzionale) che aggiunge al nome del file salvata una stringa (vedremo più avanti come è utilizzato)
[4](#4) | ` GET ` | `/stat?nome_file=` | genera statistiche partendo da un file salvato con la rotta /saveex. Restituisce massima, minima, media e varianza nei 5 giorni.
[5](#5) | ` GET ` | `/builddatabase` | leggendo i file salvati in locale con la rotta /saveex, questa rotta genera un file .txt contente la precisione delle previsioni calcolata in base ai file letti.
[6](#6) | ` POST ` | `/filter` | questa rotta chiede all'utente di inserire un body contenente un campo "nome" (per il nome della città) ed un campo "precisione" (per la percentuale di precisione che si vuole avere sulle previsioni visualizzate). Saranno visualizzate le previsioni solo dei giorni che in media (secondo i dati letti dal database costruito con la rotta apposita) hanno precisione maggiore o uguale a quella inserita dall'utente.

## **Esempi di chiamate**:

* *localhost:8080/forecast?citta=Pandoiano
  Questa chiamata visualizzerà le temperature previste a Pandoiano
  
* *localhost:8080/forecast
  In questo modo la chiamata all'API avverrà utilizzando la città di Default (Ponsacco).

* *localhost:8080/saveex?citta=Pontedera&giorno=16
  Salvo soltanto le informazioni sulla temperatura della città inserita; il file .json verrà salvato col nome "Pontedera16.json"
  
* *localhost:8080/stat?nome_file=Lucca13
  Mostra statistiche generate partendo dal file "Lucca13.json". Se non si inserisce questo parametro, la rotta leggerà un file di default.
  
* *localhost:8080/filter; 
Body: {"nome":"Pandoiano", "precisione":"100.0"}
  Questa rotta visualizzerà le previsioni delle temperature per la città di Pandoiano, soltanto nei gironi che secondo il database generato hanno una precisione del         100%


## **Elenco Puntato che spiega le rotte nello specifico**
* palle

* testicoli

* enormi

## Possibili Errori e situazioni non previste:


## Criticità e Problemi riscontrati durante lo Sviluppo:




