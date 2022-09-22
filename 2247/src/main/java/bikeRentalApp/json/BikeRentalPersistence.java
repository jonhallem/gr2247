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
    public BikeRentalPersistence() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new BikeRentalModule());
        this.writer = mapper.writer(new DefaultPrettyPrinter());
    }


    //Persistens av stedene

    public void writePlaceContainer(PlaceContainer placeContainer) throws IOException {
        this.writer.writeValue(this.getFile("places"), placeContainer);
    }

    public PlaceContainer readPlaceContainer() throws IOException {
        this.ensureSaveFileExists("places");
        return this.mapper.readValue(this.getFile("places"), PlaceContainer.class);
    }


    //Persistens av brukerne

    public void writeUserContainer(UserContainer userContainer) throws IOException {
        this.writer.writeValue(this.getFile("users"), userContainer);
    }

    public UserContainer readUserContainer() throws IOException {
        this.ensureSaveFileExists("users");
        return this.mapper.readValue(this.getFile("users"), UserContainer.class);
    }
    


    //Støttemetoder for filbehandling

    private File getFile(String fileName) {
        return getSaveFileFolderPath().resolve(fileName + ".json").toFile();
    }

    private Path getSaveFileFolderPath() {
        return Path.of("src/main/java/bikeRentalApp/json/");
    }

    private void ensureSaveFileExists(String fileName) throws IOException {
        File file = new File(getFile(fileName).toString());
        if ((!file.exists() || file.isDirectory())) {
            Files.createDirectories(getSaveFileFolderPath());
            file.createNewFile();
        }
    }

}
