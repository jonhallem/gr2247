
# Dokumentasjon - release 1

I denne releasen er følgende utarbeidet og/eller implementert:
- [Brukerhistorier](../2247/readme.md)
- [Personas](../2247/readme.md)
- Planlagt hovedmodell (se klassediagram under)
- [Prototype](../2247/readme.md)
- Bygget prosjektet i Maven
- Støtte for utvikling i [GitPod](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2247/gr2247)
- Lagt til støtte for javafx, testing og json i [pom.xml](../2247/pom.xml)
- [Brukergrensesnitt](../2247/src/main/resources/bikeRentalApp/core/BikeRentalApp.fxml)
- En fungerende [modell](../2247/src/main/java/bikeRentalApp/core/) for begrenset funksjonalitet basert på brukerhistoriene
- En [controller-klasse](../2247/src/main/java/bikeRentalApp/core/BikeRentalAppController.java) som knytter brukergrensesnittet til foreløpig funksjonalitet i modell
- En [test for User-klassen](../2247/src/test/java/bikeRentalApp/core/UserTest.java)
- [Serialiserere og deserialiserere](../2247/src/main/java/bikeRentalApp/json/) for klassene som etter hvert skal støtte lagring av tilstand i JSON-format
- 

## Klassediagram over modellen, slik som den er i release 1:

```plantuml
@startuml
BikeRentalManager --> "places: *" Place : contains
BikeRentalManager --> "users: *, loggedInUser: 1"  User : contains
Place --> "bikes: maximumNumberOfBikes" Bike : contains
User --> "bike: 1" Bike : contains

Class BikeRentalManager {
    -List<Place> places
    -List<User> users
    -User loggedInUser
    +List<Place> getPlaces()
    +List<Bike> getBikeInPlace(Place place)
    +User getLoggedInUser()
    +void logIn(String username, String password)
    +void signUp(String username, String password)
    +void rentBike(String placeName, String  bikeID)
    +void deliverBike(String placeName)
}

Class Place {
    -String name
    -int maximumNumberOfBikes
    -List<Bike> bikes
    +String getName()
    +int getMaximumNumberOfBikes()
    +List<Bike> getBikes()
    +Bike removeAndGetBike(String bikeID)
    +void addBike(Bike bike)
    -void nameValidator(String name)
    -void validateID(String iD)
    -void inputNotNullValidator(Object input)
    +Iterator<Bike> iterator()
}

Class User {
    -String username
    -String password
    -Bike bike
    +String getUserName()
    +String getPassword()
    +Bike getBike()
    +void setBike(Bike bike)
    +Bike removeAndReturnBike()
    -void validateUsername(String username)
    -void validatePassword(String password)
    +String toString()
}

Class Bike {
    -String iD
    -String type
    -String colour
    -Collection<String> validTypes
    -Collection<String> validColours
    -void validateID(String iD)
    -void validateType(String type)
    -void validateColour(String colour)
    -void inputNotNullValidator(Object input)
    +String getID()
    +String getType()
    +String getColour()
}
@enduml
```