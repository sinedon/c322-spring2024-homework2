package huydpham;

import huydpham.Model.Guitar;
import huydpham.Repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

    private static final String TEST_DATABASE_NAME = "guitars_database.txt";
    private static final String NEW_LINE = System.lineSeparator();

    @Test
    void testAddGuitar() {

        Guitar testGuitar = new Guitar("123", 999.99, "TestBuilder", "TestModel", "TestType", "TestBackWood", "TestTopWood");

        try {
            boolean result = InventoryRepository.addGuitar(testGuitar);

            assertTrue(result);

            Path filePath = Paths.get(TEST_DATABASE_NAME);
            String fileContent = Files.readString(filePath);

            String expectedData = "123,999.99,TestBuilder,TestModel,TestType,TestBackWood,TestTopWood" + NEW_LINE;
            assertTrue(fileContent.contains(expectedData));

        } catch (IOException e) {
            fail("IOException should not be thrown in this test.");
        }
    }

    @Test
    void testGetGuitar() {

        String testData = "123,999.99,TestBuilder,TestModel,TestType,TestBackWood,TestTopWood";
        try {
            Files.write(Paths.get(TEST_DATABASE_NAME), testData.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            fail("IOException should not be thrown during test setup.");
        }

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
           // assertEquals("TestTopWood", result.getTopWood());

        } catch (IOException e) {
            fail("IOException should not be thrown in this test.");
        }
    }

    @Test
    void testSearchWithSerialNumber() {
        // Arrange

        // Prepare test data
        String testData1 = "123,999.99,TestBuilder1,TestModel1,TestType1,TestBackWood1,TestTopWood1";
        String testData2 = "456,888.88,TestBuilder2,TestModel2,TestType2,TestBackWood2,TestTopWood2";

        try {
            // Write test data to the file
            Files.write(Paths.get(TEST_DATABASE_NAME), (testData1 + System.lineSeparator() + testData2).getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            fail("IOException should not be thrown during test setup.");
        }

        // Act
        try {
            List<Guitar> result = InventoryRepository.search("123", null, null, null, null, null, null);

            // Assert
            assertNotNull(result);
            assertEquals("123", result.get(0).getSerialNumber());
        } catch (IOException e) {
            fail("IOException should not be thrown in this test.");
        }
    }

    @Test
    void testSearchWithBuilder() {
        // Prepare test data
        String testData1 = "123,999.99,TestBuilder1,TestModel1,TestType1,TestBackWood1,TestTopWood1";
        String testData2 = "456,888.88,TestBuilder2,TestModel2,TestType2,TestBackWood2,TestTopWood2";

        try {
            // Write test data to the file
            Files.write(Paths.get(TEST_DATABASE_NAME), (testData1 + System.lineSeparator() + testData2).getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            fail("IOException should not be thrown during test setup.");
        }

        // Act
        try {
            List<Guitar> result = InventoryRepository.search(null, null, "TestBuilder1", null, null, null, null);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("TestBuilder1", result.get(0).getBuilder());
        } catch (IOException e) {
            fail("IOException should not be thrown in this test.");
        }
    }

	@Test
	void contextLoads() {
	}

}
