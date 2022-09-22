package bikeRentalApp.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BikeRentalAutoSaveHandler {
    
    //Metoder for å hente og sikre at lagringsmappe og fil eksisterer. -----

    private File getFile(String fileName) {
        return getSaveFileFolderPath().resolve(fileName + ".txt").toFile();
    }

    private Path getSaveFileFolderPath() {
        return Path.of("src/main/resources/saveData");
    }

    private void ensureSaveFileExists(String fileName) throws IOException {
        File file = new File(getFile(fileName).toString());
        if ((!file.exists() || file.isDirectory())) {
            Files.createDirectories(getSaveFileFolderPath());
            file.createNewFile();
        }
    }

}
