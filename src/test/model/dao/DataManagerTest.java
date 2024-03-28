package test.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import main.model.dao.DataManager;

public class DataManagerTest {

    /////////// INIT
    @Test
    public void testInitPropertiesNotNull() {

        assertNotNull(DataManager.INIT_PROPERTIES);

        assertNotNull(DataManager.SAVED_LIST_SPACE);
        assertNotNull(DataManager.SAVED_DIC_SPACE);

        assertNotNull(DataManager.SAVED_LIST_TAG);
        assertNotNull(DataManager.SAVED_DIC_TAG);

        assertNotNull(DataManager.OBJECT_TYPE_KEY);

        assertNotNull(DataManager.srcPath);
    }

    @Test
    public void testInitPropertiesValue() {
        Properties initProperties = DataManager.INIT_PROPERTIES;

        String savedListSpace = initProperties.getProperty("SAVED_LIST_SPACE");
        String savedDicSpace = initProperties.getProperty("SAVED_DIC_SPACE");

        String savedListTag = initProperties.getProperty("SAVED_LIST_TAG");
        String savedDicTag = initProperties.getProperty("SAVED_DIC_TAG");

        String objectTypeKey = initProperties.getProperty("OBJECT_TYPE_KEY");

        String srcPath = initProperties.getProperty("srcPath");

        assertEquals(savedListSpace, DataManager.SAVED_LIST_SPACE);
        assertEquals(savedDicSpace, DataManager.SAVED_DIC_SPACE);

        assertEquals(savedListTag, DataManager.SAVED_LIST_TAG);
        assertEquals(savedDicTag, DataManager.SAVED_DIC_TAG);

        assertEquals(objectTypeKey, DataManager.OBJECT_TYPE_KEY);

        assertEquals(srcPath, DataManager.srcPath);

    }

    /////////// READ
    ////// PROPERTIES
    @Test
    public void testReadANullFile() {
        Properties nullProperties = DataManager.read("");

        assertTrue(nullProperties.isEmpty());

    }

    @Test
    public void testReadAnExistingFile() {
        Properties properties = DataManager.read("./ressources/test/test_properties.conf");

        assertFalse(properties.isEmpty());
    }

    ////// TEXT FILE
    @Test
    public void testTextFileToStringArrayListNullFile() {

        ArrayList<String> textLines = DataManager.textFileToStringArrayList("");

        assertNull(textLines);
    }

    @Test
    public void testTextFileToStringArrayListNullFileWithPath() {

        ArrayList<String> textLines = DataManager.textFileToStringArrayList(Paths.get("").toFile());

        assertTrue(textLines.isEmpty());
    }

    @Test
    public void testTextFileToStringArrayListExistingFile() {
        ArrayList<String> textLines = DataManager.textFileToStringArrayList("./ressources/test/test_line_text.txt");

        assertFalse(textLines.isEmpty());

        assertNotNull(textLines.get(0));
        assertNotNull(textLines.get(1));

        assertEquals(textLines.get(0), "line1");
        assertEquals(textLines.get(1), "line2");
    }

    @Test
    public void testSave() {
        Properties savedProperties = new Properties();

        savedProperties.setProperty("saved1", "saved1");
        savedProperties.setProperty("saved2", "saved2");

        String defaultSrcPath = DataManager.srcPath;
        DataManager.srcPath = "";

        String outPutPath = "./ressources/test/saved_properties.test.conf";

        DataManager.srcPath = defaultSrcPath;

        DataManager.save(savedProperties, outPutPath);

        assertTrue(Paths.get(outPutPath).toFile().isFile());
        assertTrue(Paths.get(outPutPath).toFile().exists());

    }

    @Test
    public void testFileExist() {
        String testFile = "./ressources/test/test_line_text.txt";

        assertTrue(DataManager.fileExist(testFile));
        assertFalse(DataManager.fileExist(""));
    }

    @Test
    public void testFileIsDirectory() {
        String testDirectory = "./ressources/test";
        String testFile = "./ressources/test/test_line_text.txt";

        assertTrue(DataManager.fileIsDirectory(testDirectory));
        assertFalse(DataManager.fileIsDirectory(testFile));
    }

    @Test
    public void testObjectCollectionToAString() {
        ArrayList<Object> objects = new ArrayList<>();

        String separedListSpace = DataManager.SAVED_LIST_SPACE;

        String savedResult = separedListSpace + "obj1" + separedListSpace + "obj2" + separedListSpace + "obj3";
        objects.add("obj1");
        objects.add("obj2");
        objects.add("obj3");

        assertEquals(savedResult, DataManager.objectCollectionToAString(objects));
    }

    @Test
    public void testObjectHashMapToString() {
        HashMap<Object, Object> dic = new HashMap<Object, Object>();

        dic.put("tag1", "key1");
        dic.put("tag2", "key2");
        dic.put("tag3", "key3");

        String separedListSpace = DataManager.SAVED_LIST_SPACE;
        String separedDicSpace = DataManager.SAVED_DIC_SPACE;

        String savedResult = separedListSpace
                + "key1" + separedListSpace + "key2" + separedListSpace + "key3" + separedDicSpace + separedListSpace
                + "tag1" + separedListSpace + "tag2" + separedListSpace + "tag3";

        assertEquals(savedResult, DataManager.objectHashMapToString(dic));
    }
}
