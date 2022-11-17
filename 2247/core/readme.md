# Modulens funksjonalitet

## Core
### Bike, Place og User
Modulen core inneholder de nødvendige klassene som utgjør funksjonaliteten til applikasjonen. Blant annet Bike, Place og User - som utgjør objektene applikasjonen lager, lagrer og modifiserer. 

### BikeRentalManger
I modulen finner man også BikeRentalManager, som er hovedklassen med metoder som kontrollerer og styrer applikasjonens funksjonalitet.

### PlaceContainer og UserContainer
Itererbare klasser med samlinger av place og user objekter for bruk av persistens og kobling til JSON.

## JSON
Utgjør applikasjonens persistens, ved at BikeRentalPersistence bruker containerobjekter for å skrive og lese fra fil.
Inneholder også en defaultPlaceContainer med etablerte lokasjoner og sykler for testing og bruk av applikasjonen.

# Serializers og deserializers
Objekter som skal lagres i persistens sendes til serialiserere som skriver objektet til JSON filer. I samme grad skal objekter leses og instansieres til objekter. 

# Testing
Alle klassene i core har en testklasse som tester funksjonaliten og metodene til filene i modulen. Testingen utgjør en testdekningsgrad på 