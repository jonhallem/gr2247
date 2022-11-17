# Core-Modulens funksjonalitet

## Core
### Bike, Place og User
Modulen core inneholder de nødvendige klassene som utgjør grunnmodellen i applikasjonen. Blant annet Bike, Place og User - som utgjør objektene applikasjonen lager, lagrer og modifiserer. 

### PlaceContainer og UserContainer
Itererbare klasser med samlinger av place- og user-objekter for bruk av persistens og kobling til JSON.

### BikeRentalManger
BikeRentalManager er "overklassen", som håndterer funsjonalitet mellom bike, place og user objekter. Dette innebærer blant annet innlogging, leie av sykkel og levering av sykkel.

## Klassediagram for core
![Klassediagram](/2247/core/classDiagramCore.png "Klassediagram")

## json
### BikeRentalPersistence
Utgjør applikasjonens persistens, ved at BikeRentalPersistence bruker containerobjekter for å skrive og lese fra fil. Inneholder også en defaultPlaceContainer med etablerte lokasjoner og sykler for testing og bruk av applikasjonen.

### Serializers og deserializers
Objekter som skal lagres i persistens sendes til serialiserere som skriver objektet til JSON filer. I samme grad skal objekter leses og instansieres til objekter gjennom deserialiserere.

## Klassediagram for json
![Klassediagram](/2247/core/classdiagramJson.png "Klassediagram")

## Testing
Testklassene i core støtter enhetstesting av metoder i alle klassene. BikeRentalModuleTest tester alle serialiserere og deserialiserere.