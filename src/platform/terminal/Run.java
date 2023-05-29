package src.platform.terminal;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;


import src.data.DataCSV;
import src.key.USB;
import src.language.Language;
import src.platform.input.KeyboardInput;
import src.util.account.User;
import src.util.signature.Signature;

public class Run {
    static HashMap<String,String> primary = DataCSV.toHashMap(Paths.get("resources"+File.separator+"platform"+File.separator+"start"+File.separator+"primary.csv").toFile());
    static Language language = new Language("connection", primary.get("language"));
    static Logo logo = new Logo(100);
    static User user;
    public static void main(String[] args) {
        logo.show();
        System.out.println(language.getData().get("Welcome"));
        System.out.println(language.getData().get("WelcomeChoiceP"));
        System.out.println(language.getData().get("WelcomeChoice1"));
        System.out.println(language.getData().get("WelcomeChoice2"));
        LocalDateTime time = LocalDateTime.now();
        if(EnterIsPressed() ||  LocalDateTime.now().isAfter(time.plusSeconds(30))){
            System.out.println("Utilisation de clé USB");
            if(USBConnection()){
                System.out.println(language.getData().get("UserOpen"));

            }

        }else if(SpaceIsPressed()){

        }
    }
    public static boolean EnterIsPressed(){
        return KeyboardInput.isKeyPressed(KeyEvent.VK_ENTER);
    }
    public static boolean SpaceIsPressed(){
        return KeyboardInput.isKeyPressed(KeyEvent.VK_SPACE);
    }
    public static boolean USBConnection(){
        System.out.println(language.getData().get("USBKeyWaiting"));
        String publicKey = USB.findPublicKey();
        if(publicKey != null){
            System.out.println(language.getData().get("PublicKeyFind"));
            String privateKey = USB.findPrivateKey();
            System.out.println("PrivateKeyWaiting");
            if(privateKey != null){
                System.out.println(language.getData().get("PrivateKeyFind"));
                Signature signature = new Signature(USB.findPublicKey(),USB.findPrivateKey());
                signature.getPublicKey();
                user = new User();
                return true;
            }
        }
        return false;
    }
    public static boolean CamConnection(){
        
        return false;
    }
}
