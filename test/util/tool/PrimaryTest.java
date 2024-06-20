package util.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import exception.system.util.language.LangueageMessageNotFoundSystemException;

public class PrimaryTest {

    @Test
    public void testAllPrimaryProprety() {
        Primary.load();

        assertNotNull(Primary.DATA_MANAGER_INIT_PATH);
        assertFalse(Primary.DATA_MANAGER_INIT_PATH.isBlank());
        assertFalse(Primary.DATA_MANAGER_INIT_PATH.isEmpty());

        assertNotNull(Primary.getSystemlanguageValue());
        assertFalse(Primary.getSystemlanguageValue().isBlank());
        assertFalse(Primary.getSystemlanguageValue().isEmpty());

    }

    @Test
    public void testSetSystemlanguageValue() {
        assertDoesNotThrow(() -> {
            String language = "EN";

            Primary.setSystemlanguageValue(language);

            assertEquals(language, Primary.getSystemlanguageValue());
        });

        assertThrows(Exception.class, () -> {
            Primary.load();

            String savedLanguage = Primary.getSystemlanguageValue();
            String language = "not_aviable_language";

            Primary.setSystemlanguageValue(language);

            assertEquals(savedLanguage, Primary.getSystemlanguageValue());
        });
    }
}
