
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
- Testing av klasser i prosjektet:
  - [BikeRentalManagerTest](../2247/core/src/test/java/bikerentalapp/core/BikeRentalManagerTest.java)
  - [BikeTest](../2247/core/src/test/java/bikerentalapp/core/BikeTest.java)
  - [PlaceTest](../2247/core/src/test/java/bikerentalapp/core/PlaceTest.java)
  - [UserContainerTest](../2247/core/src/test/java/bikerentalapp/core/UserContainerTest.java)
  - [UserTest](../2247/core/src/test/java/bikerentalapp/core/UserTest.java)
  - [AppTest](../2247/fxui/src/test/java/bikerentalapp/ui/AppTest.java)
  - [BikeRentalModuleTest](../2247/core/src/test/java/bikerentalapp/json/BikeRentalModuleTest.java)
- Prosjektet er modularisert og bygget på 3-lags arkitektur med egne pom-filer
- Implementasjon av Spotbugs og Checkstyle
- Persistens ved hjelp av JSON filer (Filene, places.json og users.json, er per nå lagret flere stedet i prosjektet. Dette skal fikses til neste release)
- Rimelig god testdekning i JACOCO
- Dokumentasjon knyttet til arbeidsvaner

---

Vi har ikke endret/lagt til noe funskjonalitet (med unntak av bug-fixing) i selve applikasjonen i denne releasen. Dermed er funksjonaliteten foreløpig fremdeles basert på [brukerhistorie 1](../2247/readme.md#brukerhistorie-1).

Funksjonalitet i applikasjonen:
- Registrere ny bruker med passord
- Logge inn med eksisterende bruker
- Leie ledig sykkel fra ønsket sted
- Levere tilbake sykkel på ønsket sted

---

## Oppdatert klassediagram over modellen (core), slik som den er i release 2:

(For enkelthetens skyld  har vi unlatt å vise de mange serialisererne og deserialisererne som benyttes)

![Klassediagram](release2ClassDiagram.png "Klassediagram") 

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
    +void addPlace(String placeName, int numberOfBikes)
    +void removePlace(String placeName)
    +Place findPlace(String placeName)
    +Iterator<Place> iterator()
}

Class UserContainer {
    -List<User> users
    +List<User> getUsers()
    +void addUser(User user)
    +void removeUser(String username)
    +User findUser(String username)
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