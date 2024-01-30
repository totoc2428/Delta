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
    private final LocalDate BIRTHDATE;

    private int port;
    private int offset;
    private String hostBrodcast;

    /* Constructor : */

    public Device(String name, Person owner, LocalDate BIRTHDATE) {
        super();

        this.name = name;
        this.owner = owner;
        this.BIRTHDATE = BIRTHDATE;

        ChainObject.generate(this);
    }

    /* getter : */

    public String getName() {
        return name;
    }

    public Person getOwner() {
        return owner;
    }

    public LocalDate getBIRTHDATE() {
        return BIRTHDATE;
    }

    public int getPort() {
        return port;
    }

    public int getOffset() {
        return offset;
    }

    public String getHostBrodcast() {
        return hostBrodcast;
    }

    /* overrided method : */

    @Override
    public String toString() {
        return super.toString() + "Device [name=" + name + ", owner=" + owner + ", BIRTHDATE=" + BIRTHDATE + ", port="
                + port + ", offset="
                + offset + ", hostBrodcast=" + hostBrodcast + "]";
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
        writeInProperties(properties, "BIRTHDATE", BIRTHDATE, true);

        return properties;
    }

}