package serveur.platform;

import serveur.platform.dic.platform.DicPlatform;
import serveur.platform.dic.show.DicShow;
import serveur.platform.dic.show.Language;
import serveur.platform.dic.show.error.DicShowError;
import serveur.platform.dic.show.information.DicShowInformation;
import serveur.platform.dic.show.warning.DicShowWarning;
import serveur.platform.show.logo.Logo;
import serveur.platform.show.style.ShowStyle;

public class Platform {
    static ShowStyle showStyle;
    static Logo logo;

    static DicPlatform initDic;
    static DicShow showDic;
    static DicShowInformation showInformationDic;
    static DicShowWarning showWarningDic;
    static DicShowError showErrorDic;

    static Language language;

    static Thread listen;
    static Thread show;

    public static void main(String[] args) {
        showStyle = new ShowStyle();

        initDic = new DicPlatform("init");

        language = Language.valueOfWriteValue((String) initDic.getValue("language"));
        showDic = new DicShow(language, "util");
        showWarningDic = new DicShowWarning(language);
        showInformationDic = new DicShowInformation(language);
        showErrorDic = new DicShowError(language);

        logo = new Logo(100);
        showStyle = logo.savedShow(showStyle);

        listen = new Thread(new Runnable() {
            @Override
            public void run() {
                showStyle.addLine(showDic.getValue("welcome") + "\n");
                if (initDic.getValue("deviceIdentity").equals("null")) {
                    showStyle.addLine(showWarningDic.getValue("noDeviceIdentity"));
                    showStyle.addLine(showInformationDic.getValue("noDeviceIdentity"));
                    if (initDic.getValue("owner").equals("null")) {
                        showStyle.addLine(showWarningDic.getValue("noDeviceOwner"));
                        showStyle.addLine(showInformationDic.getValue("noDeviceOwner"));
                        if (initDic.getValue("user").equals("null")) {
                            showStyle.addLine(showWarningDic.getValue("noDeviceUser"));
                            showStyle.addLine(showInformationDic.getValue("noDeviceUser"));
                        }
                    }
                }
            }
        });

        show = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    showStyle.waitTime();
                    showStyle.show();

                }
            }
        });
        listen.start();
        show.start();

    }
}
