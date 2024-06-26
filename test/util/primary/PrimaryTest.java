package util.primary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.message.LangueageMessageNotFoundSystemException;
import exception.system.util.primary.InvalidMessagePrioritySystemException;
import exception.system.util.primary.PrimaryLoadException;
import exception.system.util.primary.PrimarySetInitFilePathSystemException;

public class PrimaryTest {

    @Before
    public void initPrimaryTest() {
        try {
            Primary.load();
        } catch (PrimaryLoadException e) {
            e.show();
        }

    }

    @Test
    public void testPrimaryLoad() {
        assertDoesNotThrow(() -> {
            Primary.setInitFilePath("./ressources/init.conf");
            Primary.load();
        });
    }

    @Test
    public void testPrimaryLoadException() {
        assertThrows(PrimaryLoadException.class, () -> {
            Primary.setInitFilePath("./test/testing_ressources_files/test.conf");

            Primary.load();
        });

        assertThrows(PrimarySetInitFilePathSystemException.class, () -> {
            Primary.setInitFilePath("./non_existing_file.conf");
        });

        assertThrows(PrimarySetInitFilePathSystemException.class, () -> {
            Primary.setInitFilePath("");
        });
    }

    @Test
    public void testAllPrimaryProprety() {

        assertNotNull(Primary.getDataManagerInitPath());
        assertFalse(Primary.getDataManagerInitPath().isBlank());
        assertFalse(Primary.getDataManagerInitPath().isEmpty());

        assertNotNull(Primary.getBlockchainManagerInitPath());
        assertFalse(Primary.getBlockchainManagerInitPath().isBlank());
        assertFalse(Primary.getBlockchainManagerInitPath().isEmpty());

        assertNotNull(Primary.getSecurityManagerInitPath());
        assertFalse(Primary.getSecurityManagerInitPath().isBlank());
        assertFalse(Primary.getSecurityManagerInitPath().isEmpty());

        assertNotNull(Primary.getSystemlanguageValue());
        assertFalse(Primary.getSystemlanguageValue().isBlank());
        assertFalse(Primary.getSystemlanguageValue().isEmpty());

    }

    @Test
    public void testGetMessagePriority() {
        assertNotNull(Primary.getMessagePriority());
        assertTrue(Primary.getMessagePriority() > 0);
    }

    @Test
    public void testSetMessagePriority() {
        assertDoesNotThrow(() -> {
            int value = 2;

            Primary.setMessagePriority(value);

            assertNotNull(value);
            assertEquals(value, Primary.getMessagePriority());

        });
    }

    @Test
    public void testSetDoneMessageInitPath() {
        assertDoesNotThrow(() -> {
            Primary.setDoneMessageInitPath("./ressources/messages/done/");
        });

        assertThrows(LangueageMessageNotFoundSystemException.class, () -> {
            Primary.setDoneMessageInitPath("./ressources/messages/");
        });

        assertThrows(PropertiesReadingSystemException.class, () -> {
            Primary.setDoneMessageInitPath("./ressources/messages/not_existing_language/");
        });
    }

    @Test
    public void testSetErrorMessageInitPath() {
        assertDoesNotThrow(() -> {
            Primary.setErrorMessageInitPath("./ressources/messages/error/");
        });

        assertThrows(LangueageMessageNotFoundSystemException.class, () -> {
            Primary.setErrorMessageInitPath("./ressources/messages/");
        });

        assertThrows(PropertiesReadingSystemException.class, () -> {
            Primary.setErrorMessageInitPath("./ressources/messages/not_existing_language/");
        });
    }

    @Test
    public void testSetInformationMessageInitPath() {
        assertDoesNotThrow(() -> {
            Primary.setDoneMessageInitPath("./ressources/messages/information/");
        });

        assertThrows(LangueageMessageNotFoundSystemException.class, () -> {
            Primary.setInformationMessageInitPath("./ressources/messages/");
        });

        assertThrows(PropertiesReadingSystemException.class, () -> {
            Primary.setInformationMessageInitPath("./ressources/messages/not_existing_language/");
        });
    }

    @Test
    public void testSetNeutralMessageInitPath() {
        assertDoesNotThrow(() -> {
            Primary.setDoneMessageInitPath("./ressources/messages/neutral/");
        });

        assertThrows(LangueageMessageNotFoundSystemException.class, () -> {
            Primary.setNeutralMessageInitPath("./ressources/messages/");
        });

        assertThrows(PropertiesReadingSystemException.class, () -> {
            Primary.setNeutralMessageInitPath("./ressources/messages/not_existing_language/");
        });
    }

    @Test
    public void testSetWarningMessageInitPath() {
        assertDoesNotThrow(() -> {
            Primary.setWarningMessageInitPath("./ressources/messages/warning/");
        });

        assertThrows(LangueageMessageNotFoundSystemException.class, () -> {
            Primary.setWarningMessageInitPath("./ressources/messages/");
        });

        assertThrows(PropertiesReadingSystemException.class, () -> {
            Primary.setWarningMessageInitPath("./ressources/messages/not_existing_language/");
        });
    }

    @Test
    public void testSetMessagePriorityException() {
        assertThrows(InvalidMessagePrioritySystemException.class, () -> {
            Primary.setMessagePriority(0);
        });

        assertThrows(InvalidMessagePrioritySystemException.class, () -> {
            Primary.setMessagePriority(-1);
        });

    }

    @Test
    public void testDoneMessagegePath() {
        assertNotNull(Primary.getDoneMessageInitPath());

        assertFalse(Primary.getDoneMessageInitPath().isBlank());
        assertFalse(Primary.getDoneMessageInitPath().isEmpty());
        assertFalse(Primary.getDoneMessageInitPath().isBlank());

        File erroPath = Paths.get(Primary.getDoneMessageInitPath()).toFile();

        assertTrue(erroPath.exists());
        assertTrue(erroPath.isDirectory());

    }

    @Test
    public void testErrorMessagePath() {
        assertNotNull(Primary.getErrorMessageFolderPath());

        assertFalse(Primary.getErrorMessageFolderPath().isBlank());
        assertFalse(Primary.getErrorMessageFolderPath().isEmpty());
        assertFalse(Primary.getErrorMessageFolderPath().isBlank());

        File erroPath = Paths.get(Primary.getErrorMessageFolderPath()).toFile();

        assertTrue(erroPath.exists());
        assertTrue(erroPath.isDirectory());

    }

    @Test
    public void testInformationMessagePath() {
        assertNotNull(Primary.getInformationMessageFolderPath());

        assertFalse(Primary.getInformationMessageFolderPath().isBlank());
        assertFalse(Primary.getInformationMessageFolderPath().isEmpty());
        assertFalse(Primary.getInformationMessageFolderPath().isBlank());

        File erroPath = Paths.get(Primary.getInformationMessageFolderPath()).toFile();

        assertTrue(erroPath.exists());
        assertTrue(erroPath.isDirectory());

    }

    @Test
    public void testWarningMessagePath() {
        assertNotNull(Primary.getWarningMessageFolderPath());

        assertFalse(Primary.getWarningMessageFolderPath().isBlank());
        assertFalse(Primary.getWarningMessageFolderPath().isEmpty());
        assertFalse(Primary.getWarningMessageFolderPath().isBlank());

        File erroPath = Paths.get(Primary.getWarningMessageFolderPath()).toFile();

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
    }

    @Test
    public void testSetSystemlanguageValueException() {
        assertThrows(LangueageMessageNotFoundSystemException.class, () -> {

            String savedLanguage = Primary.getSystemlanguageValue();
            String language = "not_aviable_language";

            Primary.setSystemlanguageValue(language);

            assertEquals(savedLanguage, Primary.getSystemlanguageValue());
        });
    }
}
