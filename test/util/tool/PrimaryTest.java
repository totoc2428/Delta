package util.tool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PrimaryTest {
    @Test
    public void testAllPrimaryProprety() {
        assertNotNull(Primary.DATA_MANAGER_INIT_PATH);
        assertFalse(Primary.DATA_MANAGER_INIT_PATH.isBlank());
        assertFalse(Primary.DATA_MANAGER_INIT_PATH.isEmpty());

    }
}
