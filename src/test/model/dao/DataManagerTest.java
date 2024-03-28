package test.model.dao;

import static org.junit.Assert.assertNotNull;

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
}
