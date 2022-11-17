
# Dokumentasjon - release 3

I denne releasen er følgende utarbeidet og/eller implementert:
- Utvidet applikasjonsfunksjonalitet knyttet til profilside og endring av passord.
  -  Denne funksjonalitet har inkludert testing, metoder og kobling til allerede etablert applikasjon. Den utvidede funksjonaliteten har også egen controller og testklasse.
- Resterende JavaDoc og feilrettinger for prosjektet.
- Testing av klasser i prosjektet:
    - 
- Prosjektet er modularisert og koblet opp mot restAPI
  - Prosjektet bruker web-server
  - Lagrer places with bikes i JSON
  - Lagrer users i JSON
  - 
- Prosjektet benytter web-server ved å sende og motta HTTP meldinger.
- Prosjektet har implementert jlink og jpackage og kan eksportere et shippable produkt.
-  Spotbugs og Checkstyle i prosjektet har 100% dekningsgrad.
- Persistens ved hjelp av JSON filer (Filene, places.json og users.json, er per nå lagret flere stedet i prosjektet. Dette skal fikses til neste release)
-  Nær 100% testdekninggrad i JACOCO. Dette er mye bedre testdekning enn i release 2. Her gjenstår kun dekning av unntakshåndtering utenfor testens kontroll (Feks. korrupte filer, systemfeil eller andre former for IOexceptions)
- Dokumentasjon knyttet til arbeidsvaner
- Diagrammer:
  - Pakkediagram av løsning
  - Sekvensdiagram
  - Klassediagram

---

I denne releasen er applikasjonen utvidet med profilside funksjonalitet knyttet opp til [brukerhistorie-2](../2247/readme.md#brukerhistorie-2). 


Funksjonalitet i applikasjonen:
- *Registrere ny bruker med passord*
- *Logge inn med eksisterende bruker*
- *Leie ledig sykkel fra ønsket sted*
- *Levere tilbake sykkel på ønsket sted*
+ Endre passord
+ Logge ut av applikasjonen

---