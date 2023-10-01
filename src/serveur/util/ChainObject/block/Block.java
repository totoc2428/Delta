package serveur.util.ChainObject.block;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import serveur.util.ChainObject.ChainObject;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Block extends ChainObject {

    private String lastHash;
    private ArrayList<String> elements;
    private static final int stamp = 100;

    /* Construcor */
    /* Base constructor */
    public Block(Key signature, String lastHash, ArrayList<String> elements) {
        super(signature);
        this.lastHash = lastHash;
        this.elements = elements;
    }

    /* Key init constructor */
    public Block(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /* File init constructor */
    public Block(File fileName) {
        this(readBlock(fileName).getSignature(), readBlock(fileName).getLastHash(), readBlock(fileName).getElements());
    }

    /* New element consctrutor */
    public Block() {
        this(new Key(), "", new ArrayList<String>());
    }

    /* Object method */
    public void add(String hash) {
        if (remainingLine() > 0) {
            this.elements.add(hash);
        } else {
            // exception
        }
    }

    public boolean isValid() {
        Block block = new Block(new Key(Key.PublicKeyfromString(lastHash)));
        return getElements().size() == stamp && block != null && block.isValid();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public int remainingLine() {
        return elements.size() - stamp;
    }

    /* Getter and setter method */
    public ArrayList<String> getElements() {
        return elements;
    }

    public String getLastHash() {
        return lastHash;
    }

    public static int getStamp() {
        return stamp;
    }

    /* Override method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lastHash == null) ? 0 : lastHash.hashCode());
        result = prime * result + ((elements == null) ? 0 : elements.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Block other = (Block) obj;
        if (lastHash == null) {
            if (other.lastHash != null)
                return false;
        } else if (!lastHash.equals(other.lastHash))
            return false;
        if (elements == null) {
            if (other.elements != null)
                return false;
        } else if (!elements.equals(other.elements))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Block [lastHash=" + lastHash + ", elements=" + elements + "] extended " + super.toString();
    }

    @Override
    public Properties toWriteFormat() {
        Properties properties = super.toWriteFormat();
        properties.setProperty("lastHash", lastHash);
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : elements) {
            stringBuilder.append(str);
            stringBuilder.append(" ");
        }
        properties.setProperty("elements", lastHash);
        return properties;

    }

    /* Static Method */
    /* Read a block saved in a file */
    protected static Block readBlock(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            String name = properties.getProperty("name");
            Key lastBlock = new Key(Key.PublicKeyfromString((String) properties.get("lastHash")));
            return new Block(lastBlock, name,
                    new ArrayList<>(Arrays.asList(properties.getProperty("elements").split(" "))));
        } else {
            return null;
        }
    }

}
