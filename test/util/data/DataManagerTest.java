package util.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import exception.system.util.data.DataManagerLoadException;
import exception.system.util.data.DataManagerSetInitFilePathSystemException;
import exception.system.util.data.PropertiesReadingSystemException;
import io.jsonwebtoken.lang.Arrays;
import util.primary.Primary;
import exception.system.util.data.WriteInAFileSystemException;
import exception.system.util.primary.PrimaryLoadException;

public class DataManagerTest {
    @Before
    public void initDataMangerTest() {
        try {
            Primary.load();
        } catch (PrimaryLoadException e) {
            e.show();
        }
    }

    @Test
    public void testLoad() {
        assertDoesNotThrow(() -> {
            DataManager.load();
        });

        assertThrows(DataManagerLoadException.class, () -> {
            DataManager.setInitFilePath("./test/testing_ressources_files/test.conf");

            DataManager.load();
        });

        assertThrows(DataManagerSetInitFilePathSystemException.class, () -> {
            DataManager.setInitFilePath("./non_existing_file.conf");
        });

        assertThrows(DataManagerSetInitFilePathSystemException.class, () -> {
            DataManager.setInitFilePath("");
        });
    }

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
    }

    @Test
    public void testReadAFileException() {
        assertThrows(PropertiesReadingSystemException.class, () -> {
            String src = "";

            Properties properties = DataManager.readAFile(src);

            assertNull(properties);
        });
    }

    @Test
    public void testDataManagerWriteInAFile() {
        assertDoesNotThrow(() -> {
            String destinationFile = "./test/testing_ressources_files/write_in_a_file_test.conf";
            Properties properties = new Properties();

            properties.put("key1", "value1");
            properties.put("key2", "value2");

            DataManager.writeInAFile(properties, destinationFile);

            Properties properties2 = DataManager.readAFile(destinationFile);

            assertNotNull(properties2);
            assertFalse(properties2.isEmpty());
            assertEquals(properties, properties2);
        });

    }

    @Test
    public void testDataManagerWriteInAFileException() {
        assertThrows(WriteInAFileSystemException.class, () -> {
            String destinationFile = null;
            Properties properties = new Properties();

            properties.put("key1", "value1");
            properties.put("key2", "value2");

            DataManager.writeInAFile(properties, destinationFile);
        });
    }

    @Test
    public void testGetters() {
        assertNotNull(DataManager.getDicTag());
        assertNotNull(DataManager.getListTag());

        assertNotNull(DataManager.getSavedListSpace());
        assertNotNull(DataManager.getSavedDicSpace());

        assertFalse(DataManager.getDicTag().isEmpty());
        assertFalse(DataManager.getDicTag().isBlank());
        assertFalse(DataManager.getListTag().isEmpty());
        assertFalse(DataManager.getListTag().isBlank());

        assertFalse(DataManager.getSavedDicSpace().isEmpty());
        assertFalse(DataManager.getSavedListSpace().isEmpty());
        assertFalse(DataManager.getSavedDicSpace().isBlank());
        assertFalse(DataManager.getSavedListSpace().isBlank());

    }

    @Test
    public void testListToSavingFormat() {
        String[] list = new String[] { "truc", "truc1", "truc2" };

        String listInSavedFormat = DataManager.listToSavingFormat(Arrays.asList(list));

        assertNotNull(listInSavedFormat);
        assertFalse(listInSavedFormat.isEmpty());
        assertFalse(listInSavedFormat.isBlank());

        assertEquals("truc" + DataManager.getSavedListSpace() + "truc1" + DataManager.getSavedListSpace() + "truc2",
                listInSavedFormat);

    }

    @Test
    public void testDicToSavingFormat() {
        HashMap<String, String> dic = new HashMap<String, String>();

        dic.put("key1", "value1");
        dic.put("key2", "value2");
        dic.put("key3", "value3");

        String dicInSavedFormat = DataManager.dicToSavingFormat(dic);

        assertNotNull(dicInSavedFormat);
        assertFalse(dicInSavedFormat.isEmpty());
        assertFalse(dicInSavedFormat.isBlank());

        assertEquals("key1" + DataManager.getSavedListSpace() + "key2" + DataManager.getSavedListSpace() + "key3"
                + DataManager.getSavedDicSpace() + "value1" + DataManager.getSavedListSpace() + "value2"
                + DataManager.getSavedListSpace() + "value3", dicInSavedFormat);

    }

    @Test
    public void testSavingFormatToAList() {
        String listInSavedFormat = "truc" + DataManager.getSavedListSpace() + "truc1" + DataManager.getSavedListSpace()
                + "truc2";

        Collection<String> listResult = DataManager.savingFormatToAList(listInSavedFormat);
        Collection<String> listCheck = Arrays.asList(new String[] { "truc", "truc1", "truc2" });

        assertNotNull(listResult);
        assertFalse(listResult.isEmpty());

        assertEquals(listResult, listCheck);
    }

    @Test
    public void testSavingFormatToADic() {
        String dicInSavedFormat = "key1" + DataManager.getSavedListSpace() + "key2" + DataManager.getSavedListSpace()
                + "key3" + DataManager.getSavedDicSpace() + "value1" + DataManager.getSavedListSpace() + "value2"
                + DataManager.getSavedListSpace() + "value3";

        Map<String, String> checkDic = (Map<String, String>) new HashMap<String, String>();
        checkDic.put("key1", "value1");
        checkDic.put("key2", "value2");
        checkDic.put("key3", "value3");

        Map<String, String> resultDic = DataManager.savingFormatToADic(dicInSavedFormat);

        assertNotNull(resultDic);
        assertFalse(resultDic.isEmpty());

        assertEquals(resultDic, checkDic);

    }
}
