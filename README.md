# FE2_Zerlegung_ILSAnsbach
## Motivation
Eine einfache Möglichkeit eine Alarmdepesche auszudrucken mit der eingebauten FE2 Funktionalität. Kartendruck geht in der Regel nur mit AlarmMonitor 4, deswegen dieser Workaround um eine Karte zu generieren (evtl. werden es in Zukunft mehrere).
## Aktueller Stand
Das ganze ist noch work-in-progress. Aktueller Stand: 
* REST Api => GET: http://localhost:8080/generate?lat=49.123&lng=10.500)
* Erstellt eine Karte mit Hilfe von Google Static Maps API
* Fragt Hydranten im Umkreis per API von Wasserkarte.Info ab und fügt sie als Marker in die Karte ein
* Speicherung der Karte an beliebigem Ort (kein Screenshot, direkter Download)
## Beispiel
![Alt text](/example.png?raw=true "Optional Title")
## TODO
* Konfiguration glatt ziehen und ausführbares Jar bauen, aktuell nur über IDE.
