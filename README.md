# FE2_Kartengenerierung
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
## Konfiguratoin
* Aktuell wird kein Artefakt gebaut, kann daher nur in IDE gestartet werden.
* Einstellungen über application.properties
```
# MANDATORY: The Google Cloud API Key authorized to access 'Maps Static API'
gcp.apiKey=123456

# OPTIONAL: If configured in Cloud console, sign each request for improved security.
gcp.signingKey=123546

# MANDATORY: The target file location. Supported file endings: png,gif,jpg
outputFile=C:\\temp\\image.png

# OPTIONAL: The Wasserkarte.info access token
wk.token=123456

# OPTIONAL: Custom icons per sourceType of Wasserkarte.info. The icons need to be hosted somewhere and must be reachable from Internet!
# If not defined for any or all sourceTypeIds, fallback to default icon.
# Example: <id>=<url>;<id2>=<url2> etc.
wk.customIcons=1=https://bit.ly/Hydrant16O.png;2=https://bit.ly/Hydrant16U.png;3=https://bit.ly/Hydrant16W.png
```
