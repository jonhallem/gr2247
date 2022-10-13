
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
- Testing av klasser i prosjektet:
  - [BikeRentalManagerTest](../2247/core/src/test/java/bikeRentalApp/core/BikeRentalManagerTest.java)
  - [BikeTest](../2247/core/src/test/java/bikeRentalApp/core/BikeTest.java)
  - [PlaceTest](../2247/core/src/test/java/bikeRentalApp/core/PlaceTest.java)
  - [UserContainerTest](../2247/core/src/test/java/bikeRentalApp/core/UserContainerTest.java)
  - [UserTest](../2247/core/src/test/java/bikeRentalApp/core/UserTest.java)
  - [AppTest](../2247/fxui/src/test/java/bikeRentalApp/ui/AppTest.java)
- Prosjektet er modularisert og bygget på 3-lags arkitektur med egne pom filer
- Implementasjon av Spotbugs og Checkstyle
- Persistens ved hjelp av JSON filer
- Rimelig god testdekning i JACOCO
- Dokumentasjon knyttet til arbeidsvaner

## Oppdatert klassediagram over modellen (core), slik som den er i release 2:

(Midlertidig bilde, inntil plantUML-implementasjonen fungerer.)
(For enkelthetens skyld  har vi unlatt å vise de mange serialisererne og deserialisererne som benyttes)

![alt text](release2ClassDiagram.png "Klassediagram") 

```plantuml
@startuml
BikeRentalManager --> "bikeRentalPersisence: 1" BikeRentalPersistence
BikeRentalManager --> "loggedInUser: 1" User : contains
BikeRentalManager --> PlaceContainer : uses
BikeRentalManager --> UserContainer : uses
BikeRentalPersistence --> PlaceContainer : reads/writes
BikeRentalPersistence --> UserContainer : reads/writes
PlaceContainer --> "places: *" Place : contains
UserContainer --> "users: *" User : contains
Place --> "bikes: maximumNumberOfBikes" Bike : contains
User --> "bike: 1" Bike : contains

Class BikeRentalManager {
    -User loggedInUser
    -BikeRentalPersistence bikeRentalPersistence
    +List<Place> getPlaces()
    +User getLoggedInUser()
    +Bike getUserBike()
    +void logIn(String username, String password)
    +void signUp(String username, String password)
    +void rentBike(String placeName, String  bikeID)
    +void deliverBike(String placeName)
}

Class BikeRentalPersistence {
    -ObjectMapper mapper
    -ObjectWriter writer
    +void writePlaceContainer(PlaceContainer placeContainer)
    +PlaceContainer readPlaceContainer()
    +void writeUserContainer(UserContainer userContainer)
    +UserContainer readUserContainer()
    -File getFile(String fileName)
    -Path getSaveFileFolderPath()
    -void ensureSaveFileExists(String fileName)
}

Class PlaceContainer {
    -List<Place> places
    +List<Place> getPlaces()
    +Iterator<Place> iterator()
}

Class UserContainer {
    -List<User> users
    +List<User> getUsers()
    +void addUser(User user)
    +User findUser(String userName)
    +Iterator<User> iterator()
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
    -void maximumNumberOfBikesValidator(int number)
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