package bikeRentalApp.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import bikeRentalApp.core.BikeRentalManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.Set;

public class BikeRentalPersistence {
    
    // public enum BikeRentalParts{
    //     PLACE, USER
    // }

    // private ObjectMapper mapper;

    // public BikeRentalPersistence() {
    //     mapper = createObjectMapper();
    // }

    // public static SimpleModule createJacksonModule(Set<BikeRentalParts> parts) {
    //     return new BikeRentalModule(parts);
    //   }
    
    // public static ObjectMapper createObjectMapper() {
    //     return createObjectMapper(EnumSet.allOf(BikeRentalParts.class));
    // }

    // public static ObjectMapper createObjectMapper(Set<BikeRentalParts> parts) {
    //     return new ObjectMapper()
    //       .registerModule(createJacksonModule(parts));
    //   }

    // public BikeRentalManager readTodoModel(Reader reader) throws IOException {
    //     return mapper.readValue(reader, BikeRentalManager.class);
    // }

    // public void writeTodoModel(BikeRentalManager bikeRentalManager, Writer writer) throws IOException {
    //     mapper.writerWithDefaultPrettyPrinter().writeValue(writer, bikeRentalManager);
    }
