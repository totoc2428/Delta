package util.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Paths;

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
    public void testDoneMessagegePath() {
        assertNotNull(Primary.DONE_MESSAGE_FOLDER_PATH);

        assertFalse(Primary.DONE_MESSAGE_FOLDER_PATH.isBlank());
        assertFalse(Primary.DONE_MESSAGE_FOLDER_PATH.isEmpty());
        assertFalse(Primary.DONE_MESSAGE_FOLDER_PATH.isBlank());

        File erroPath = Paths.get(Primary.DONE_MESSAGE_FOLDER_PATH).toFile();

        assertTrue(erroPath.exists());
        assertTrue(erroPath.isDirectory());

    }

    @Test
    public void testErrorMessagePath() {
        assertNotNull(Primary.ERROR_MESSAGE_FOLDER_PATH);

        assertFalse(Primary.ERROR_MESSAGE_FOLDER_PATH.isBlank());
        assertFalse(Primary.ERROR_MESSAGE_FOLDER_PATH.isEmpty());
        assertFalse(Primary.ERROR_MESSAGE_FOLDER_PATH.isBlank());

        File erroPath = Paths.get(Primary.ERROR_MESSAGE_FOLDER_PATH).toFile();

        assertTrue(erroPath.exists());
        assertTrue(erroPath.isDirectory());

    }

    @Test
    public void testInformationMessagePath() {
        assertNotNull(Primary.INFORMATION_MESSAGE_FOLDER_PATH);

        assertFalse(Primary.INFORMATION_MESSAGE_FOLDER_PATH.isBlank());
        assertFalse(Primary.INFORMATION_MESSAGE_FOLDER_PATH.isEmpty());
        assertFalse(Primary.INFORMATION_MESSAGE_FOLDER_PATH.isBlank());

        File erroPath = Paths.get(Primary.INFORMATION_MESSAGE_FOLDER_PATH).toFile();

        assertTrue(erroPath.exists());
        assertTrue(erroPath.isDirectory());

    }

    @Test
    public void testWarningMessagePath() {
        assertNotNull(Primary.WARNING_MESSAGE_FOLDER_PATH);

        assertFalse(Primary.WARNING_MESSAGE_FOLDER_PATH.isBlank());
        assertFalse(Primary.WARNING_MESSAGE_FOLDER_PATH.isEmpty());
        assertFalse(Primary.WARNING_MESSAGE_FOLDER_PATH.isBlank());

        File erroPath = Paths.get(Primary.WARNING_MESSAGE_FOLDER_PATH).toFile();

        assertTrue(erroPath.exists());
        assertTrue(erroPath.isDirectory());

    }

    @Test
    public void testSetSystemlanguageValue() {

        assertDoesNotThrow(() -> {
            String language = "en";

            Primary.setSystemlanguageValue(language);

            assertEquals(language, Primary.getSystemlanguageValue());
        });

        Primary.load();

        assertThrows(LangueageMessageNotFoundSystemException.class, () -> {

            String savedLanguage = Primary.getSystemlanguageValue();
            String language = "not_aviable_language";

            Primary.setSystemlanguageValue(language);

            assertEquals(savedLanguage, Primary.getSystemlanguageValue());
        });
    }
}
