package util.message;

import java.util.Properties;

import exception.system.util.data.PropertieReadingSystemException;
import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.data.DataManager;
import util.tool.Primary;

public abstract class SystemMessage {
    private String message;
    private static Properties messageHub;

    public SystemMessage(String messageCode) {
        this.message = messageHub.getProperty(messageCode);
    }

    public String getMessage() {
        return message;
    }

    protected static void load(String messageSrcPath) throws LangueageMessageNotFoundSystemException {
        try {
            messageHub = DataManager
                    .readAFile(messageSrcPath + Primary.getSystemlanguageValue().toLowerCase() + "/messages.conf");

            System.out.println(messageSrcPath + " : \n" + messageHub);
        } catch (PropertieReadingSystemException e) {
            throw new LangueageMessageNotFoundSystemException();
        }
    }
}
