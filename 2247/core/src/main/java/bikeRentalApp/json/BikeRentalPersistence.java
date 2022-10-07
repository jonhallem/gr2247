package bikeRentalApp.json;


import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import bikeRentalApp.core.PlaceContainer;
import bikeRentalApp.core.UserContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class BikeRentalPersistence {

    private ObjectMapper mapper;
    private ObjectWriter writer;


    //Konstruktør

    /**
     * Constructs a new {@code BikeRentalPersistence} object, used to write {@code PlaceContainer}
     * objects and {@code UserContainer} objects to file, as well as read them.
     */
    public BikeRentalPersistence() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new BikeRentalModule());
        this.writer = mapper.writer(new DefaultPrettyPrinter());
    }


    //Persistens av stedene

    /**
     * Writes a {@code PlaceContainer} object to the "places.json" file
     * 
     * @param placeContainer the {@code PlaceContainer} object to write to file
     * @throws IOException
     */
    public void writePlaceContainer(PlaceContainer placeContainer) throws IOException {
        this.writer.writeValue(this.getFile("places"), placeContainer);
    }

    /**
     * Reads "places.json" and returns a new {@code PlaceContainer} object based on the data
     * in the file
     * @return a {@code PlaceContainer} object
     * @throws IOException
     */
    public PlaceContainer readPlaceContainer() throws IOException {
        this.ensureSaveFileExists("places");
        return this.mapper.readValue(this.getFile("places"), PlaceContainer.class);
    }


    //Persistens av brukerne

    /**
     * Writes a {@code UserContainer} object to the "users.json" file
     * 
     * @param userContainer the {@code UserContainer} object to write to file
     * @throws IOException
     */
    public void writeUserContainer(UserContainer userContainer) throws IOException {
        this.writer.writeValue(this.getFile("users"), userContainer);
    }

    /**
     * Reads "users.json" and returns a new {@code UserContainer} object based on the data
     * in the file
     * @return a {@code UserContainer} object
     * @throws IOException
     */
    public UserContainer readUserContainer() throws IOException {
        this.ensureSaveFileExists("users");
        return this.mapper.readValue(this.getFile("users"), UserContainer.class);
    }
    


    //Støttemetoder for filbehandling

    /**
     * Returns a {@code File} object representing the path to the JSON file with 
     * the name "fileName".
     * 
     * @param fileName a String, the name of the JSON file (without the .json
     * file ending)
     * @return a {@code File} object
     */
    private File getFile(String fileName) {
        return getSaveFileFolderPath().resolve(fileName + ".json").toFile();
    }

    /**
     * Returns the {@code Path} object to the location of the JSON-file to read from or write to.
     * 
     * @return a {@code Path} object
     */
    private Path getSaveFileFolderPath() {
        return Path.of("gr2247/2247/core/src/main/java/bikeRentalApp/json");
    }


    // TODO: Vurder å slette denne, mulig den ikke trengs når lagringsfilene uansett er i repoet
    /**
     * Ensures that the JSON file to read from actually exists before reading,
     * avoiding a FileNotFoundException. 
     * 
     * @param fileName the name of the file (without the .json file ending) 
     * to ensure the existanse of
     * @throws IOException
     */
    private void ensureSaveFileExists(String fileName) throws IOException {
        File file = new File(getFile(fileName).toString());
        if ((!file.exists() || file.isDirectory())) {
            Files.createDirectories(getSaveFileFolderPath());
            file.createNewFile();
        }
    }

}
