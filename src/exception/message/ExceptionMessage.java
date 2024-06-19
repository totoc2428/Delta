package exception.message;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;
import exception.system.util.data.PropertieReadingSystemException;
import exception.system.util.language.LangueageMessageNotFoundSystemExcetion;
import util.data.DataManager;
import util.tool.Primary;

public abstract class ExceptionMessage {
    public static Properties messages;

    public static String getAnExceptionMessage(String systemExceptionCode) {
        return (String) messages.get(systemExceptionCode);
    }

    public static void load() throws LangueageMessageNotFoundSystemExcetion {
        try {
            File file = Paths
                    .get(Primary.ERROR_MESSAGE_FOLDER_PATH + Primary.getSystemlanguageValue().toLowerCase()
                            + "/messages.conf")
                    .toFile();

            System.out.println(file.exists());
            System.out.println(file);

            messages = DataManager
                    .readAFile(file);
        } catch (PropertieReadingSystemException e) {
            throw new LangueageMessageNotFoundSystemExcetion();
        }
    }

}
