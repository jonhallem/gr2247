
# Dokumentasjon - release 2

I denne releasen er følgende utarbeidet og/eller implementert:
- Javadocs for følgende klasser:
  - Bike
  - BikeRentalAppController
  - BikeRentalManager
  - Place
  - PlaceContainer
  - User
  - UserContainer
  - Serializer
  - Deserializer
  - 

## Oppdatert klassediagram over modellen, slik som den er i release 2:

```plantuml
Class BikeRentalManager {
    -User loggedInUser
    -BikeRentalPersistence bikeRentalPersistence
    +List<Place> getPlaces()
    +User getLoggedInUser()
    +void logIn(String username, String password)
    +void signUp(String username, String password)
    +void rentBike(String placeName, String  bikeID)
    +void deliverBike(String placeName)
}
```