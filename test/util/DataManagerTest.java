package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Properties;

import org.junit.Test;

public class DataManagerTest {
    @Test
    public void testReadAFile() {
        String src = "./test/ressources/test.conf";

        assertDoesNotThrow(() -> {
            Properties properties = DataManager.readAFile(src);

            assertNotNull(properties);
            assertEquals(properties.getProperty("test"), "test");
        });

    }
}
