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

    public Block(Key signature, String lastHash, ArrayList<String> elements) {
        super(signature);
        this.lastHash = lastHash;
        this.elements = elements;
    }

    public Block(String fileName) {
        this(Block.readBlock(fileName).getSignature(),
                Block.readBlock(fileName).getLastHash(),
                Block.readBlock(fileName).getElements());
    }

    public Block() {
        this(new Key(), "", new ArrayList<String>());
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public String getLastHash() {
        return lastHash;
    }

    public String toString() {
        String str = lastHash;
        for (String elm : elements) {
            str += elm;
        }
        return str;
    }

    public void add(String hash) {
        this.elements.add(hash);
    }

    public boolean isValid() {
        Block block = new Block(lastHash + ".csv");
        return getElements().size() == stamp && block != null && block.isValid();
    }

    public static Block readBlock(String fileName) {
        Block block = null;
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
        return block;
    }

}
