# Bike Rental App


BikeRentalApp er en applikasjon for korttids- (og muligens) langtidsleie av sykler i Trondheim. 
Trondheim Bysykkel's nr. 1 utfordrer?

Kortidsleie av en ledig sykkel startes ved et tastetrykk, og gjøres ved et av BikeRentalApps utleiesteder. 
Like enkelt er det å levere! 

---

## Sykler:
Applikasjonen vil tilby et bredt utvalg av sykkeltyper for utleie:
- Landeveissykkel 
- Terrengsykkel
- Elektrisk terrengsykkel 
- Fjellsykkel
- Fatbike 
- Tandemsykkel
- Elektrisk tandemsykkel
- Christianiasykkel  
- Hybridsykkel

## Utleiesteder:
For å være tilgjengelig for Bike Rental App's kunder, er vi tilgjengelige der du er!
- Bymarka
- Munkholmen
- Lerkendal
- Tiller
- Nidarosdomen
- Gløshaugen
- Lilleby

---

## Prototype som skjermbilder, brukerhistorie 1

![alt text](skjermbilder/BikeRentalAppSkjermbilde1.jpg "GUI for applikasjonen før utlån")
![alt text](skjermbilder/BikeRentalAppSkjermbilde2.jpg "GUI for applikasjonen etter utlån")
![alt text](skjermbilder/BikeRentalAppSkjermbilde3.jpg "GUI for applikasjonen ved innlevering")

---

## Prototype som skjermbilder, brukerhistorie 2 og 3

![alt text](skjermbilder/BikeRentalAppSkjermbilde4.jpg "GUI for applikasjonens profilvindu")

---


## Brukerhistorie-1

"Som en tidsskvist hverdagspendler ønsker jeg å kunne sykle til og/eller fra jobb, uten å måtte forplikte meg til å sykle begge veier."

## Brukerhistorie-2

"Som en vanlig person med samme passord på alle digitale tjenester, har jeg - Mona (23) - blitt hacket, og mitt eneste passord er lekket ut på internettet. Jeg må derfor endre passordene på alle tjenester"

#### Personas-1

Petter (36 år) hadde en svært aktiv livsstil, men har vært på latsia de siste årene. Han ønsker å få mer aktivitet inn i hans ellers travle hverdag. Den tiden han alikevel bruker på pendling til jobb kunne Petter like greit brukt til fysisk aktivtet! Grunnet jobbsituasjon og privatliv er det ikke alltids Petter kan sykle begge veier. Derfor er det gunstig å kunne være fleksibel. 

Petter bor på Tiller, men arbeider like ved Nidarosdomen - en lang pendlertur han like godt kan bruke på å holde helsen ved like. 
Ettersom Petter er ganske ukjent med sykkel som fremkomstmiddel, men regner med å ha stor progresjon, ønsker han å kunne prøve ulike typer sykler uten å måtte forplikte seg til en gitt modell. 

#### Personas-2

Mona (23) kommer fra Kristiansand, og studerer medisin i Trondheim. Hun er veldig aktiv på sosiale medier, og bruker mye av tiden sin på mobil og nett. På de digitale tjenestene hun er på, bruker hun samme passord på alle - da det er stressende å huske på flere ulike passord. En morgen der hun sykler til skolen, får hun en e-post fra en ukjent person med passordet hennes i klartekst. En av de digitale tjenestene hennes har blitt hacket, og nå er passordet hun bruker til alle kontoene sine offentlig. Hun må bruke mye tid og energi på å endre passord på alle tjenestene sine, der blant BikeRentalApp.


### Viktig å kunne se
- En oversikt over tilgjengelige sykler på de ulike utleieområdene (Brukerhistorie 1)
- En oversikt over ulike tilgjengelige sykkeltyper (Brukerhistorie 1)
- Hvilken sykkel du leier (Brukerhistorie 1)
- En historikk over syklene du har leid (Brukerhistorie 3)

### Viktig å kunne gjøre 
- Leie og levere en sykkel ved ulike utleieområder (Brukerhistorie 1)
- Endre passord (Brukerhistorie 2)

---

## Bruk av applikasjonen

Applikasjonen kan kjøres ved bruk av **mvn javafx:run** fra mappen *GR2247/2247/fxui*. 

Om det oppstår dependency-problemer når 'mvn javafx:run' kjøres i /fxui, naviger tilbake til rotnivå /2247 og kjør kommandoen *'mvn clean -U install'*. Dette burde fikse problemet.

### Kjøre applikasjonen med lokal jersey server

For å starte applikasjonen med lokal server er det nødvendig å bruke en *split terminal*, noe som kan gjøres ved å høyreklikke på terminalen i VS Code og velge *Split Terminal*. 
Deretter navigeres til mappen *2247/integrationtests* i den ene terminalen, for så å starte den lokale jersey serveren ved å kalle **mvn jetty:run -D"jetty:port=8080"**. For å starte applikasjonen navigeres det til mappen *2247/fxui* i den andre terminalen, og kalles **mvn -Premoteapp javafx:run**. Dette starter applikasjonen med *remoteapp* profilen, som kjører *RemoteApp.java* og starter applikasjonen med oppkobling til server.  

## Kodekvalitet

### Tester

Tester er blitt skrevet for majoriteten av all funksjonalitet i alle modulene. 
Testdekningsgraden er undersøkt ved bruk av JaCoCo (se neste avsnitt). Det er oppnådd et dekningsgrad på 99% core modulen, og 80% på fxui modulen. 

### Verktøy

For å teste kodekvalitet og testdekningsgrad er JaCoCo, Checkstyle og Spotbugs brukt. Verktøy kjøres fra mappen *GR2247/2247*, etter installert prosjekt. 
JaCoCo er brukt for å undersøke testdekningsgraden. Dette gjøres ved å kalle: **mvn clean jacoco:prepare-agent install jacoco:report**
Checkstyle er brukt for å undersøke kodestilen, i henhold til google_checks.xml. Checkstyle kalles ved: **mvn checkstyle:check**
Checkstyle rapporterer ikke om noen klare brudd, men gir et sett med advarsler. Dette er advarsler som stort sett omhandler indentering. 
Spotbugs er brukt for å finne utrygg og ukonvensjonell bruk av kode. Spotbugs kalles ved: **mvn spotbugs:check**, og kan også sees ved **mvn spotbugs:gui** i et mer oversiktlig brukergrensesnitt. 
JaCoCo, Checkstyle og Spotbugs kan kalles fra rotmappen, *2247*, eller fra hver av modulene: *core*, *fxui*, *integrationtests*, *rest*. 

## Arbeidesvaner

### Bruk av verktøy

JaCoCo er blitt aktivt brukt for å undersøke testdekningsgraden slik at eventuelle hull i testing har blitt avdekket underveis. 
Videre er det tenkt å i større grad benytte oss av tilbakemeldingene fra verktøyene Checkstyle og Spotbugs for å forbedre kodekvaliteten vår. 

### Parprogrammering

Parprogrammering er brukt ved flere anledninger. 
Særlig ved koding av nye klasser, og ved feilsøking, har parprogrammering vist seg å være et svært nyttig verktøy. 

### Forgrening 

Større arbeidsoppgaver har blitt utført i en ny grein. Dette for å minimere sannsynligheten for at allerede utført arbeid blir tilsudlet. 
Når en grein skal sammensveises med master gjøres det ved en *"merge-request"* gjennom GitLab. Dette for at flere personer skal kontrollere hva som utføres ved større arbeidsoppgaver før alt settes sammen. 
Nye greiner skal navngis med NTNU brukernavn, type arbeidsoppgave og kort beskrivelse, samt hvilken klasse greinen omfatter adskilt med understreker. F.eks *brukernavn-create_test_nyKlasse*