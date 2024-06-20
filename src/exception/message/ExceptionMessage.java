package exception.message;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;
import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.data.DataManager;
import util.tool.Primary;

public abstract class ExceptionMessage {
    public static Properties messages;

    public static String getAnExceptionMessage(String systemExceptionCode) {
        return (String) messages.get(systemExceptionCode);
    }

    public static void load() throws LangueageMessageNotFoundSystemException {
        try {
            File file = Paths
                    .get(Primary.ERROR_MESSAGE_FOLDER_PATH + Primary.getSystemlanguageValue().toLowerCase()
                            + "/messages.conf")
                    .toFile();

            messages = DataManager
                    .readAFile(file);
        } catch (PropertiesReadingSystemException e) {
            throw new LangueageMessageNotFoundSystemException();
        }
    }

}
