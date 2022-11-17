# Modulens funksjonalitet

## FXUI

### BikeRentalAppController
Hovedkontrolleren som tar for seg endringer i viewet. Kontrollerklassen presenterer logikk og funksjonalitet i core gjennom data fra remote og appAccess.

### ProfilePageController
ProfilePageControlleren tar for seg endringer i viewet når bruker går inn i profilsiden i applikasjonen. 

### App
Klassen ansvarlig for å kjøre applikasjonen.

### BikeRentalManagerAccess


### DirectBikeRentalManagerAccess


### RemoteBikeRentalManagerAccess


## Resources
Inneholder javafx fxml filer med applikasjonens utseende for hovedsiden og profilsiden, i tillegg til en img mappe med bilder og ikoner brukt i applikasjonen.

# Testing
Testingen består av 2 klasser som hver tar for seg en kontroller. AppTest tester BikeRentalAppController og at riktig innhold blir presentert korrekt basert på data fra dataoverføring/access/remote og funksjonalitet i core. I tillegg testet det at innholdet blir presentert riktig på profilsiden i ProfilePageTest. Testene i GUIet blir testet ved hjelp av testfx.