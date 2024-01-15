package app;

import java.io.File;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Properties;

import util.data.Folder;
import util.data.Prop;
import util.security.Key;

public class Main {
    private static Properties initConfig;
    private static Properties deviceConfig;
    private static Properties mainConfig;
    private static PublicKey publicKey;

    public static void main(String[] args) {

        init();
    }

    private static void init() {
        if (checkRequirement()) {
            initConfig = Prop.read(Paths.get("./resources/config/init.conf").toFile());
            deviceConfig = Prop.read(Paths.get(initConfig.getProperty("DeviceConfig")).toFile());
        } else {
            // TODO exception
        }
    }

    private static void checkDeviceRequirement() {
        if (!deviceConfig.getProperty("owner").isEmpty() && !deviceConfig.getProperty("owner").isBlank()
                && !deviceConfig.getProperty("owner").equals("null")) {
            publicKey = Key.stringToPublicKey(deviceConfig.getProperty("owner"));
        } else {
            if (deviceConfig.getProperty("owner").equals("null")) {
                publicKey = null;
            } else {
                // TODO exception
            }
        }
    }

    private static void initDevice() {

    }

    private static boolean checkRequirement() {
        return checkFileConfigurationRequirement();
    }

    private static boolean checkFileConfigurationRequirement() {
        return checkAConfigurationFileRequirement(Prop.read(Paths.get("./resources/config/init.conf").toFile()));
    }

    private static boolean checkAConfigurationFileRequirement(Properties properties) {
        boolean check = true;
        if (properties != null) {
            for (Object object : properties.keySet()) {
                if (object instanceof String) {
                    String str = (String) object;
                    if (str.contains("Config")) {
                        check = checkAFileRequirement(Paths.get(properties.getProperty(str)).toFile());
                        if (check) {
                            check = checkAConfigurationFileRequirement(Prop.read(
                                    Paths.get(properties.getProperty(str)).toFile()));
                        } else {
                            break;
                        }
                    } else if (str.contains("SrcFolder")) {
                        check = checkAFolderRequirement(Paths.get(properties.getProperty(str)).toFile());
                    }
                    if (!check) {
                        break;
                    }
                }
            }
        } else {
            check = false;
        }
        return check;
    }

    private static boolean checkAFileRequirement(File file) {
        return file.isFile() && file.exists();
    }

    private static boolean checkAFolderRequirement(File file) {
        if (file.isDirectory()) {
            if (!file.exists()) {
                Folder.createDirectory(file.getName(), file);
            }
            return true;
        }
        return false;
    }
}
