package bikeRentalApp.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class PlaceTest {
    
    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med valide argumenter")
    public void testNewPlace_validArguments() {
        
        Place place1 = new Place("Bymarka", 1);

        Place place2 = new Place("Bymarka1", 120);

        Place place3 = new Place("bymarka", 40);
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med ugyldige ID-argumenter")
    public void testNewPlace_unvalidName() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Place(null, 3); }, "IllegalArgument skal utløses om name er 'null'.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("", 3); }, "IllegalArgument skal utløses om name er en tom streng.");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Place("  ", 3); }, "IllegalArgument skal utløses om name er en streng bestående av kun mellomrom.");            
    }

}
