package bikerentalapp.json;

/**
 * A class for storing a String of the default PlaceContainer JSON.
 */
public class DefaultPlaceContainer {

    private static final String DEFAULT_PLACECONTAINER = """
            {
                "places" : [ {
                  "name" : "Bymarka",
                  "maximumNumberOfBikes" : "20",
                  "bikes" : [ {
                    "iD" : "BIKEID01",
                    "type" : "Terrengsykkel",
                    "colour" : "Gul"
                  }, {
                    "iD" : "BIKEID02",
                    "type" : "Terrengsykkel",
                    "colour" : "Svart"
                  }, {
                    "iD" : "BIKEID03",
                    "type" : "Elektrisk terrengsykkel",
                    "colour" : "Hvit"
                  }, {
                    "iD" : "BIKEID04",
                    "type" : "Elektrisk terrengsykkel",
                    "colour" : "Blå"
                  }, {
                    "iD" : "BIKEID05",
                    "type" : "Fjellsykkel",
                    "colour" : "Blå"
                  }, {
                    "iD" : "BIKEID06",
                    "type" : "Fatbike",
                    "colour" : "Grønn"
                  } ]
                }, {
                  "name" : "Munkholmen",
                  "maximumNumberOfBikes" : "10",
                  "bikes" : [ {
                    "iD" : "BIKEID07",
                    "type" : "Hybridsykkel",
                    "colour" : "Rosa"
                  }, {
                    "iD" : "BIKEID08",
                    "type" : "Hybridsykkel",
                    "colour" : "Blå"
                  }, {
                    "iD" : "BIKEID09",
                    "type" : "Hybridsykkel",
                    "colour" : "Svart"
                  }, {
                    "iD" : "BIKEID10",
                    "type" : "Landeveissykkel",
                    "colour" : "Blå"
                  } ]
                }, {
                  "name" : "Lerkendal",
                  "maximumNumberOfBikes" : "30",
                  "bikes" : [ {
                    "iD" : "BIKEID11",
                    "type" : "Landeveissykkel",
                    "colour" : "Lilla"
                  }, {
                    "iD" : "BIKEID12",
                    "type" : "Landeveissykkel",
                    "colour" : "Hvit"
                  }, {
                    "iD" : "BIKEID13",
                    "type" : "Hybridsykkel",
                    "colour" : "Hvit"
                  }, {
                    "iD" : "BIKEID14",
                    "type" : "Tandemsykkel",
                    "colour" : "Svart"
                  }, {
                    "iD" : "BIKEID15",
                    "type" : "Christianiasykkel",
                    "colour" : "Svart"
                  }, {
                    "iD" : "BIKEID16",
                    "type" : "Elektrisk tandemsykkel",
                    "colour" : "Grønn"
                  } ]
                }, {
                  "name" : "Tiller",
                  "maximumNumberOfBikes" : "30",
                  "bikes" : [ {
                    "iD" : "BIKEID17",
                    "type" : "Terrengsykkel",
                    "colour" : "Grønn"
                  }, {
                    "iD" : "BIKEID18",
                    "type" : "Terrengsykkel",
                    "colour" : "Svart"
                  }, {
                    "iD" : "BIKEID19",
                    "type" : "Hybridsykkel",
                    "colour" : "Grønn"
                  }, {
                    "iD" : "BIKEID20",
                    "type" : "Hybridsykkel",
                    "colour" : "Blå"
                  }, {
                    "iD" : "BIKEID21",
                    "type" : "Landeveissykkel",
                    "colour" : "Rød"
                  } ]
                }, {
                  "name" : "Nidarosdomen",
                  "maximumNumberOfBikes" : "20",
                  "bikes" : [ {
                    "iD" : "BIKEID22",
                    "type" : "Hybridsykkel",
                    "colour" : "Blå"
                  }, {
                    "iD" : "BIKEID23",
                    "type" : "Hybridsykkel",
                    "colour" : "Svart"
                  }, {
                    "iD" : "BIKEID24",
                    "type" : "Christianiasykkel",
                    "colour" : "Hvit"
                  } ]
                }, {
                  "name" : "Gløshaugen",
                  "maximumNumberOfBikes" : "30",
                  "bikes" : [ {
                    "iD" : "BIKEID25",
                    "type" : "Tandemsykkel",
                    "colour" : "Gul"
                  }, {
                    "iD" : "BIKEID26",
                    "type" : "Tandemsykkel",
                    "colour" : "Svart"
                  }, {
                    "iD" : "BIKEID27",
                    "type" : "Landeveissykkel",
                    "colour" : "Lilla"
                  }, {
                    "iD" : "BIKEID28",
                    "type" : "Landeveissykkel",
                    "colour" : "Rosa"
                  }, {
                    "iD" : "BIKEID29",
                    "type" : "Hybridsykkel",
                    "colour" : "Blå"
                  }, {
                    "iD" : "BIKEID30",
                    "type" : "Hybridsykkel",
                    "colour" : "Grønn"
                  } ]
                }, {
                  "name" : "Lilleby",
                  "maximumNumberOfBikes" : "20",
                  "bikes" : [ {
                    "iD" : "BIKEID31",
                    "type" : "Hybridsykkel",
                    "colour" : "Blå"
                  }, {
                    "iD" : "BIKEID32",
                    "type" : "Landeveissykkel",
                    "colour" : "Grønn"
                  } ]
                } ]
              }
                """;

    /**
     * Returns the default PlaceContainer JSON as a string.
     *
     * @return the default PlaceContainer JSON as a string.
     */
    public static String getDefaultPlaceContainerString() {
        return DEFAULT_PLACECONTAINER;
    }
}
