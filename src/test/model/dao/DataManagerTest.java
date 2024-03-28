package test.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import main.model.dao.DataManager;

public class DataManagerTest {

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
}
