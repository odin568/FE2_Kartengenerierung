# FE2_Kartengenerierung
## Motivation
Eine einfache Möglichkeit eine Alarmdepesche auszudrucken mit der eingebauten FE2 Funktionalität. Kartendruck geht in der Regel nur mit AlarmMonitor 4, deswegen dieser Workaround um eine Karte zu generieren (evtl. werden es in Zukunft mehrere).
## Aktueller Stand
Das Ganze ist noch work-in-progress. Aktueller Stand: 
* Applikation bietet einen REST Service an => GET: http://localhost:8080/overview?lat=49.123&lng=10.500)
* Erstellt eine Karte mithilfe von [Google Static Maps API](https://developers.google.com/maps/documentation/maps-static) 
  (API Key und optionaler Signing Key werden benötigt)
* Fragt Hydranten im Umkreis per API von Wasserkarte.info ab und fügt sie als Marker in die Karte ein (optional). 
  Dabei werden (bis zu 5) eigene Icons unterstützt, müssen aber getrennt gehostet werden. 
  Die Applikation prüft, ob die Icons erreichbar sind. Falls nicht: Fallback auf default icon.
* Speicherung der Karte an beliebigem Ort (kein Screenshot, direkter Download)
## Beispiel
With custom icons
![Alt text](screenshots/readme/overview_customicons.png?raw=true "Generated overview with custom icons")
Without custom icons (can also be a mixture)
![Alt text](screenshots/readme/overview_noicons.png?raw=true "Generated overview without custom icons")
## Konfiguration
* Aktuell wird kein Artefakt gebaut, kann daher nur in IDE gestartet werden.
* Einstellungen über application.properties
```
# MANDATORY: The Google Cloud API Key authorized to access 'Maps Static API'
gcp.maps.apiKey=123456

# OPTIONAL: If configured in Cloud console for static maps apiKey, sign each request for improved security.
gcp.maps.signingKey=123546

# OPTIONAL: The Google Cloud API Key authorized to access 'Directions API'
gcp.directions.apiKey=123456

# MANDATORY: The target folder
output.folder=C:\\temp\\

# MANDATORY: The output format. Supported: png8,png32,gif,jpg,jpg-baseline
output.format=png32

# OPTIONAL: The Wasserkarte.info access token
wk.token=123456

# OPTIONAL: Custom icons per sourceType of Wasserkarte.info. The icons need to be hosted somewhere and must be reachable from Internet!
# If not defined for any or all sourceTypeIds, fallback to default icon.
# Example: <id>=<url>;<id2>=<url2> etc.
wk.customIcons=1=https://bit.ly/Hydrant16O.png;2=https://bit.ly/Hydrant16U.png;3=https://bit.ly/Hydrant16W.png
```
## TODO
* Artefakt bauen
* Evtl. als Docker container oder mit WinSW ServiceWrapper
* Routen über directions api hinzufügen