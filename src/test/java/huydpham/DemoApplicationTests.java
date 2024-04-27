package huydpham;

import huydpham.Model.Builder;
import huydpham.Model.Guitar;
import huydpham.Model.Type;
import huydpham.Model.Wood;
import huydpham.Repository.InventoryFileRepository;
import huydpham.Repository.InventoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration
class DemoApplicationTests {

    private static final String TEST_DATABASE_NAME = "guitars_database.txt";
    private static final String NEW_LINE = System.lineSeparator();

    @BeforeEach
    void setup() {
        try {
            Guitar testGuitar = new Guitar("123", 999.99, Builder.FENDER, "TestModel", Type.ACOUSTIC, Wood.INDIAN_ROSEWOOD, Wood.CEDAR);
            boolean result1 = InventoryFileRepository.addGuitar(testGuitar);

            Guitar secondTestGuitar = new Guitar("456", 799.99, Builder.MARTIN, "AnotherModel", Type.ELECTRIC, Wood.MAHOGANY, Wood.MAPLE);
            boolean result2 = InventoryFileRepository.addGuitar(secondTestGuitar);

            assertTrue(result1 && result2, "Failed to add guitars to the database");

        } catch (IOException e) {
            fail("Failed to add guitars to the database", e);
        }
    }

    @AfterEach
    void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(TEST_DATABASE_NAME));
        } catch (IOException e) {
            fail("Failed to delete the database", e);
        }
    }
    @Test
    void testAddGuitar() {
        Guitar testGuitar = new Guitar("123", 999.99, Builder.FENDER, "TestModel", Type.ACOUSTIC, Wood.INDIAN_ROSEWOOD, Wood.CEDAR);

        try {
            boolean result = InventoryFileRepository.addGuitar(testGuitar);

            assertTrue(result, "Expected addGuitar to return true");

            Path filePath = Paths.get(TEST_DATABASE_NAME);
            String fileContent = Files.readString(filePath);

            String expectedData = "123,999.99,Fender,TestModel,acoustic,Indian_Rosewood,Cedar" + NEW_LINE;
            assertTrue(fileContent.contains(expectedData), "Expected data not found in the file");

        } catch (IOException e) {
            fail("IOException should not be thrown in this test", e);
        }
    }

    @Test
    void testAddSecondGuitar() {
        Guitar secondTestGuitar = new Guitar("456", 799.99, Builder.MARTIN, "AnotherModel", Type.ELECTRIC, Wood.MAHOGANY, Wood.MAPLE);

        try {
            boolean result = InventoryFileRepository.addGuitar(secondTestGuitar);

            assertTrue(result, "Expected addGuitar to return true");

            Path filePath = Paths.get(TEST_DATABASE_NAME);
            String fileContent = Files.readString(filePath);

            String expectedFirstGuitarData = "123,999.99,Fender,TestModel,acoustic,Indian_Rosewood,Cedar" + NEW_LINE;
            String expectedSecondGuitarData = "456,799.99,Martin,AnotherModel,electric,Mahogany,Maple" + NEW_LINE;

            assertTrue(fileContent.contains(expectedFirstGuitarData), "Expected data of the first guitar not found in the file");
            assertTrue(fileContent.contains(expectedSecondGuitarData), "Expected data of the second guitar not found in the file");

        } catch (IOException e) {
            fail("IOException should not be thrown in this test.", e);
        }
    }

    @Test
    void testGetGuitar() {

        try {
            Guitar result = InventoryFileRepository.getGuitar("123");

            assertNotNull(result);
            assertEquals("123", result.getSerialNumber());
            assertEquals(999.99, result.getPrice());
            assertEquals(Builder.FENDER, result.getBuilder());
            assertEquals("TestModel", result.getModel());
            assertEquals(Type.ACOUSTIC, result.getType());
            assertEquals(Wood.INDIAN_ROSEWOOD, result.getBackWood());
            assertEquals(Wood.CEDAR, result.getTopWood());

        } catch (IOException e) {
            fail("IOException should not be thrown in this test");
        }
    }

    @Test
    void testSearchWithBuilder() {
        try {
            List<Guitar> result = InventoryFileRepository.search(null, null, String.valueOf(Builder.MARTIN), null, null, null, null);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(Builder.MARTIN, result.get(0).getBuilder());
        } catch (IOException e) {
            fail("IOException should not be thrown in this test.");
        }
    }
}
