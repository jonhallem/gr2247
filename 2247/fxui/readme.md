# Modulens funksjonalitet

## FXUI

### BikeRentalAppController
Hovedkontrolleren som tar for seg endringer i viewet. Kontrolleren aksesserer funksjonalitet i modellen gjennom BikeRentalManagerAccess.

### ProfilePageController
ProfilePageControlleren tar for seg endringer i viewet når bruker går inn i profilsiden i applikasjonen. Kontrolleren aksesserer funksjonalitet i modellen gjennom BikeRentalManagerAccess.

### App
Klasse som initialiserer kjøring av appliasjonen med direkte modellaksess.

### RemoteApp
Klasse som initialiserer kjøring av appliasjonen med remote modellaksess.

### BikeRentalManagerAccess
Interface som definerer metoder kontrollere trenger i modellen.


### DirectBikeRentalManagerAccess
Implementerer metodene i BikeRentalManagerAccess gjennom direkte metodekall i BikeRentalManager.


### RemoteBikeRentalManagerAccess
Implementerer metodene i BikeRentalManagerAccess gjennom http-requests til REST API.


## Resources
Inneholder fxml-filer med applikasjonens grafiske brukergrensesnitt for hovedsiden og profilsiden, i tillegg til en img mappe med bilder og ikoner brukt i applikasjonen.

# Testing
Testingen består av 2 klasser som hver tar for seg en kontroller. AppTest tester BikeRentalAppController og at innhold blir presentert korrekt basert på data fra BikeRentalAccess. I tillegg testet det at innholdet blir presentert riktig på profilsiden i ProfilePageTest. Testene i GUIet blir testet ved hjelp av TestFX.