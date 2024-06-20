package util.message;

import java.util.Properties;

import exception.system.util.data.PropertieReadingSystemException;
import exception.system.util.language.LangueageMessageNotFoundSystemExcetion;
import util.data.DataManager;

public abstract class SystemMessage {
    private String message;
    private static Properties messageHub;

    public SystemMessage(String messageCode) {
        this.message = messageHub.getProperty(messageCode);
    }

    public String getMessage() {
        return message;
    }

    protected void load(String messageSrcPath) throws LangueageMessageNotFoundSystemExcetion {
        try {
            messageHub = DataManager.readAFile(messageSrcPath);
        } catch (PropertieReadingSystemException e) {
            throw new LangueageMessageNotFoundSystemExcetion();
        }
    }
}
