package chainobject.hardwere;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Properties;

import chainobject.ChainObject;
import chainobject.person.Person;
import util.data.Prop;
import util.security.Key;

public class Device extends ChainObject {
    protected static final String SRC_PATH = ChainObject.SRC_PATH + Prop
            .read(Paths.get(Prop.read(Paths.get("./resources/config/init.conf").toFile())
                    .getProperty("ChainObjectConfig")).toFile())
            .getProperty("DeviceChainObjectSrcFolder");

    /**
     * The name of the current device.
     */
    private String name;

    /**
     * The owner of the current device. A device can be shared betwen several
     * person. The owner of the device earn any coin mined by the device on the
     * network.
     */
    private Person owner;

    /**
     * The birth date is the date who the device is activated for the fist time. In
     * this case a private public key pair will be define.
     */
    private LocalDate birthDate;

    /* Constructor : */

    public Device(String name, Person owner, LocalDate birthDate) {
        super();
        ChainObject.generate(this);
    }

    /* overrided method : */

    @Override
    public String toString() {
        return super.toString() + "Device [name=" + name + ", owner=" + owner + ", birthDate=" + birthDate + "]";
    }

    @Override
    public void write() {
        super.write(this.initWrite(), SRC_PATH + Key.publicKeyToString(getPublicKey()));
    }

    @Override
    protected Properties initWrite() {
        Properties properties = super.initWrite();

        writeInProperties(properties, "name", name, true);
        writeInProperties(properties, "owner", owner, true);
        writeInProperties(properties, "birthDate", birthDate, true);

        return properties;
    }

}
