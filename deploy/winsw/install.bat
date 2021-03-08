if not exist "maps" mkdir maps

FE2_Kartengenerierung.exe install
FE2_Kartengenerierung.exe status
FE2_Kartengenerierung.exe start
FE2_Kartengenerierung.exe status

timeout /t 10 /nobreak

start "" http://localhost:8080/actuator/health

pause