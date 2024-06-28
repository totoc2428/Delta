package model.manager;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Before;
import org.junit.Test;

import exception.system.model.manager.chainobject.ChainObjectManagerSetInitFilePathSystemException;
import exception.system.util.primary.PrimaryLoadException;
import util.primary.Primary;

public class ChainObjectManagerTest {

    @Before
    public void initChainObjectTest() {
        try {
            Primary.load();
        } catch (PrimaryLoadException e) {
            e.show();
        }
    }

    @Test
    public void testload() {
        assertDoesNotThrow(() -> {
            ChainObjectManager.setInitFilePath(Primary.getChainObjectManagerInitPath());
            ChainObjectManager.load();
        });

        assertThrows(ChainObjectManagerSetInitFilePathSystemException.class, () -> {
            ChainObjectManager.setInitFilePath("./not_existing_file.conf");
        });

        assertThrows(ChainObjectManagerSetInitFilePathSystemException.class, () -> {
            ChainObjectManager.setInitFilePath("./ressources/testing_ressources_files/test.conf");
            ChainObjectManager.load();
        });
    }
}
