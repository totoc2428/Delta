package util.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.Properties;

import org.junit.Test;

public class DataManagerTest {

    @Test
    public void testFolderNameToAStringArrayList() {
        ArrayList<String> foldersName = new ArrayList<String>();

        foldersName.add("test1");
        foldersName.add("test2");
        foldersName.add("test3");

        assertEquals(foldersName,
                DataManager.folderNameToAStringArrayList("./test/testing_ressources_files/test_folder"));

    }

    @Test
    public void testReadAFile() {

        assertDoesNotThrow(() -> {
            String src = "./test/testing_ressources_files/test.conf";
            Properties properties = DataManager.readAFile(src);

            assertNotNull(properties);
            assertEquals(properties.getProperty("test"), "test");
        });

        assertThrows(Exception.class, () -> {
            String src = "";

            Properties properties = DataManager.readAFile(src);

            assertNull(properties);
        });

    }
}
