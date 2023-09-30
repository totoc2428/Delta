package serveur.util.ChainObject.block;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import serveur.util.ChainObject.ChainObject;
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

    /* Construcor */
    /* File init constructor */
    public Block(String fileName) {
        this(Block.readBlock(fileName).getSignature(),
                Block.readBlock(fileName).getLastHash(),
                Block.readBlock(fileName).getElements());
    }

    /* Constructor */
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
        Block block = new Block(lastHash + ".csv");
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
        return "Block [lastHash=" + lastHash + ", elements=" + elements + "]";
    }

    /* Static Method */
    /* Read a block saved in a file */
    public static Block readBlock(String fileName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(fileName));
            String name = properties.getProperty("name");
            ArrayList<String> keyChain = new ArrayList<String>();
            for (String str : properties.getProperty("keyChain").split(" ")) {
                keyChain.add(str);
            }
            Key lastBlock = new Key(properties.getProperty("lastkey"));
            return new Block(lastBlock, name, keyChain);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
