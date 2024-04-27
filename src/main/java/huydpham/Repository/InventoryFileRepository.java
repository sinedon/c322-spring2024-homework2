package huydpham.Repository;
import huydpham.Model.Builder;
import huydpham.Model.Guitar;
import huydpham.Model.Type;
import huydpham.Model.Wood;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryFileRepository {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String DATABASE_NAME = "guitars_database.txt";
    private static void appendToFile(Path path, String content)
            throws IOException {
        Files.write(path,
                content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public static boolean addGuitar(Guitar guitar) throws IOException {
        Path path = Paths.get(DATABASE_NAME);
        String data = guitar.getSerialNumber() + "," +
                guitar.getPrice() + "," +
                guitar.getBuilder() + "," +
                guitar.getModel() + "," +
                guitar.getType() + "," +
                guitar.getBackWood() + "," +
                guitar.getTopWood() + NEW_LINE;
        appendToFile(path, data);
        return true;
    }

    public static Guitar getGuitar(String serialNumber) throws IOException {
        Path path = Paths.get(DATABASE_NAME);
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            String[] guitarData = line.split(",");
            if (guitarData.length == 7 && guitarData[0].equals(serialNumber)) {
                return createGuitarFromData(guitarData);
            }
        }

        return null;
    }

    public static List<Guitar> findAll() throws IOException {
        List<Guitar> result = new ArrayList<>();
        Path path = Paths.get(DATABASE_NAME);
        List<String> data = Files.readAllLines(path);

        for (String line : data) {
            String[] guitarData = line.split(",");
            if (guitarData.length == 7) {
                result.add(createGuitarFromData(guitarData));
            } else {
                System.out.println("Invalid data in the database: " + line);
            }
        }

        return result;
    }

    private static Guitar createGuitarFromData(String[] guitarData) {
        return new Guitar(
                guitarData[0],                             // serialNumber
                Double.parseDouble(guitarData[1]),         // price
                Builder.valueOf(guitarData[2].toUpperCase()),  // builder
                guitarData[3],                             // model
                Type.valueOf(guitarData[4].toUpperCase()),      // type
                Wood.valueOf(guitarData[5].toUpperCase()),      // backWood
                Wood.valueOf(guitarData[6].toUpperCase())       // topWood
        );
    }


    public static List<Guitar> search(String serialNumber, Double price, String builder, String model, String type, String backWood, String topWood) throws IOException {
        List<Guitar> allGuitars;
        try {
            allGuitars = findAll();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return allGuitars.stream()
                .filter(guitar -> serialNumber == null || guitar.getSerialNumber().equals(serialNumber))
                .filter(guitar -> price == null || guitar.getPrice() == price)
                .filter(guitar -> builder == null || guitar.getBuilder().toString().equals(builder))
                .filter(guitar -> model == null || guitar.getModel().equals(model))
                .filter(guitar -> type == null || guitar.getType().toString().equals(type))
                .filter(guitar -> backWood == null || guitar.getBackWood().toString().equals(backWood))
                .filter(guitar -> topWood == null || guitar.getTopWood().toString().equals(topWood))
                .collect(Collectors.toList());
    }

    public static void clearDatabase() throws IOException {
        Path path = Paths.get(DATABASE_NAME);
        Files.deleteIfExists(path); // Delete the file if it exists
    }
}
