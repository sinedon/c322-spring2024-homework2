package huydpham;

import huydpham.Model.Guitar;
import huydpham.Repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

    private static final String TEST_DATABASE_NAME = "guitars_database.txt";
    private static final String NEW_LINE = System.lineSeparator();

    @Test
    void testAddGuitar() {

        // Create a test guitar
        Guitar testGuitar = new Guitar("123", 999.99, "TestBuilder", "TestModel", "TestType", "TestBackWood", "TestTopWood");

        // Act
        try {
            boolean result = InventoryRepository.addGuitar(testGuitar);

            // Assert
            assertTrue(result, "Expected addGuitar to return true");

            // Check if the guitar data is added to the file
            Path filePath = Paths.get(TEST_DATABASE_NAME);
            String fileContent = Files.readString(filePath);

            String expectedData = "123,999.99,TestBuilder,TestModel,TestType,TestBackWood,TestTopWood" + NEW_LINE;
            assertTrue(fileContent.contains(expectedData), "Expected data not found in the file");

        } catch (IOException e) {
            fail("IOException should not be thrown in this test.", e);
        }
    }

    @Test
    void testAddSecondGuitar() {

        // Create a second test guitar
        Guitar secondTestGuitar = new Guitar("456", 799.99, "AnotherBuilder", "AnotherModel", "AnotherType", "AnotherBackWood", "AnotherTopWood");

        // Act
        try {
            boolean result = InventoryRepository.addGuitar(secondTestGuitar);

            // Assert
            assertTrue(result, "Expected addGuitar to return true");

            // Check if the second guitar data is added to the file
            Path filePath = Paths.get(TEST_DATABASE_NAME);
            String fileContent = Files.readString(filePath);

            String expectedFirstGuitarData = "123,999.99,TestBuilder,TestModel,TestType,TestBackWood,TestTopWood" + NEW_LINE;
            String expectedSecondGuitarData = "456,799.99,AnotherBuilder,AnotherModel,AnotherType,AnotherBackWood,AnotherTopWood" + NEW_LINE;

            assertTrue(fileContent.contains(expectedFirstGuitarData), "Expected data of the first guitar not found in the file");
            assertTrue(fileContent.contains(expectedSecondGuitarData), "Expected data of the second guitar not found in the file");

        } catch (IOException e) {
            fail("IOException should not be thrown in this test.", e);
        }
    }

    @Test
    void testGetGuitar() {

        // Act
        try {
            Guitar result = InventoryRepository.getGuitar("123");

            // Assert
            assertNotNull(result);
            assertEquals("123", result.getSerialNumber());
            assertEquals(999.99, result.getPrice());
            assertEquals("TestBuilder", result.getBuilder());
            assertEquals("TestModel", result.getModel());
            assertEquals("TestType", result.getType());
            assertEquals("TestBackWood", result.getBackWood());
            assertEquals("TestTopWood", result.getTopWood());

        } catch (IOException e) {
            fail("IOException should not be thrown in this test.");
        }
    }

    @Test
    void testSearchWithBuilder() {
        // Act
        try {
            List<Guitar> result = InventoryRepository.search(null, null, "AnotherBuilder", null, null, null, null);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("AnotherBuilder", result.get(0).getBuilder());
        } catch (IOException e) {
            fail("IOException should not be thrown in this test.");
        }
    }
}
