# Rest-Modulens funksjonalitet

## restapi
### BikeRentalService
BikeRentalService-klassen definerer grensesnittet for REST API. Her implementeres metoder som kan kalles gjennom http-requests av RemoteBikeRentalAccess (i fxui-modulen). Metodene kaller så videre på metoder i BikeRentalManager (i core-modulen). Klassen svarer så RemoteBikeRentalAccess med http-responses, som inneholder deseriliserte User objekter og PlaceContainer objecter. Dette sørger for å videre skille ui-et fra modellen. 

Under er et sekvensdiagram som viser metodekall mellom klassene når applikasjonen kjøres med RemoteBikeRentalAccess.

![Sekvensdiagram](/docs/sequenceDiagram.png "Sekvensgram")

## restserver
### BikeRentalConfig
BikeRentalConfig-klassen brukes av web.xml i integrationtests-modulen for å konfigurere serveren. Den registrerer BikeRentalService som definerer http-requests til serveren, en ObjectMapper for serialisering og deserialisering av objekter som skal sendes/mottas, og JacksonFeature, som støtter serialisering og deserialisering.

### BikeRentalModuleObjectMapperProvider
Klassen henter en ObjectMapper med serialisererne og deserialisererne nødvendig for serialisering og deserialisering av modellobjektene i core (User, Bike, Place, PlaceContainer og UaerContainer). Disse brukes når objekter skal sendes over http.

## Klassediagram
![Klassediagram](/2247/rest/classDiagram.png "Klassediagram")