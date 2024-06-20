package util.message;

import java.util.HashMap;
import java.util.Properties;

import exception.system.util.data.PropertiesReadingSystemException;
import exception.system.util.language.LangueageMessageNotFoundSystemException;
import util.data.DataManager;
import util.message.done.DoneSystemMessage;
import util.message.error.ErrorSystemMessage;
import util.message.information.InformationSystemMessage;
import util.message.neutral.NeutralSystemMessage;
import util.message.warning.WarningSystemMessage;
import util.tool.Primary;

public abstract class SystemMessage {
    private String message;
    private static HashMap<String, Properties> messageHub = new HashMap<String, Properties>();

    public SystemMessage(String code, String messageCode) {
        this.message = messageHub.get(code).getProperty(messageCode);
    }

    public String getMessage() {
        return message;
    }

    protected static void load(String savedCode, String messageSrcPath) throws LangueageMessageNotFoundSystemException {
        try {
            messageHub.put(savedCode, DataManager
                    .readAFile(messageSrcPath + Primary.getSystemlanguageValue().toLowerCase() + "/messages.conf"));
        } catch (PropertiesReadingSystemException e) {
            throw new LangueageMessageNotFoundSystemException();
        }
    }

    public static void load() throws LangueageMessageNotFoundSystemException {
        DoneSystemMessage.loadWithCode();
        ErrorSystemMessage.loadWithCode();
        InformationSystemMessage.loadWithCode();
        NeutralSystemMessage.loadWithCode();
        WarningSystemMessage.loadWithCode();
    }

    public static void reset() {
        messageHub.clear();
    }
}
