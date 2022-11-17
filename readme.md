# Group gr2247 repository 
 
 [![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2247/gr2247) 


## Applikasjon for sykkelutleie:

Funksjonalitet til appen skal gjøre det mulig for bruker å leie sykler på utvalgte sykkelparkeringer i Trondheim. Applikasjonen skal tilby korttidsleie av sykler.

[Lenke til applikasjonens funksjonalitet](2247/readme.md)

## Struktur:

**Prosjektet bruker maven til bygging og kjøring.**

Koden til applikasjonen ligger i mappen *2247*.
- Prosjektmappen består av pom.xml, source-mappe (med videre prosjekt og testfiler) og target.
- Prosjektmappen inneholder også en mappe med skjermbilder, som er til bruk under beskrivelse av [applikasjonens funksjonalitet](2247/readme.md).

Dokumentasjon ligger i mappen [*docs*](docs/), med markdownfiler for hver release.

## Starte applikajsonen

Applikasjonen kan kjøres ved bruk av **mvn javafx:run** fra mappen *GR2247/2247/fxui*. 

Om det oppstår dependency-problemer når 'mvn javafx:run' kjøres i /fxui, naviger tilbake til rotnivå /2247 og kjør kommandoen *'mvn clean -U install'*. Dette burde fikse problemet.

### Kjøre applikasjonen med lokal jersey server

For å starte applikasjonen med lokal server er det nødvendig å bruke en *split terminal*, noe som kan gjøres ved å høyreklikke på terminalen i VS Code og velge *Split Terminal*. 
Deretter navigeres til mappen *2247/integrationtests* i den ene terminalen, for så å starte den lokale jersey serveren ved å kalle **mvn jetty:run -D"jetty:port=8080"**. For å starte applikasjonen navigeres det til mappen *2247/fxui* i den andre terminalen, og kalles **mvn -Premoteapp javafx:run**. Dette starter applikasjonen med *remoteapp* profilen, som kjører *RemoteApp.java* og starter applikasjonen med oppkobling til server.  

## Bildehenvisning til ikoner brukt i applikasjonen:

cyclingIcon: "https://www.flaticon.com/free-icons/cycling" Cycling icons created by kosonicon - Flaticon

bicycleIcon: "https://www.flaticon.com/free-icons/bicycle" Bicycle icons created by Freepik - Flaticon