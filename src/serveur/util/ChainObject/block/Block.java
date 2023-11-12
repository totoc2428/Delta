package serveur.util.chainobject.block;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import serveur.util.chainobject.ChainObject;
import serveur.util.data.prop.DataProp;
import serveur.util.security.Key;

public class Block extends ChainObject {

    private Key lastHash;
    private ArrayList<String> elements;
    private static final int stamp = 100;

    /**
     * Base constructor :
     * take parameter to make a Block
     * 
     * @param signature of the block.
     * @param lastHash  of the bloc is the
     *                  signature.PublickeyToString() value of the previous block in
     *                  the chain
     * @param elements  elements an array list of hash of action saved in the block.
     *                  (The type String hash is foudable in
     *                  {@link serveur.util.security.Hash})
     */
    public Block(Key signature, boolean encryptedSave, Key lastHash, ArrayList<String> elements) {
        super(signature, encryptedSave);
        this.lastHash = lastHash;
        this.elements = elements;
    }

    /**
     * Key init constructor
     * Take a Key signature to make a Block
     * 
     * @param signature must be placed on the SRC_PATH atribute of the Block type.
     */
    public Block(Key signature) {
        this(Paths.get(SRC_PATH + signature.getPublickeyString() + ".prop").toFile());
    }

    /**
     * File init constructor
     * Take a File fileName to make a Block.
     * 
     * @param fileName must be a type properties (.prop) and respect the syntax of
     *                 key-value pairs.
     */
    public Block(File fileName) {
        this(readBlock(fileName).getSignature(), readBlock(fileName).getEncryptedSave(),
                readBlock(fileName).getLastHash(), readBlock(fileName).getElements());
    }

    /* Object method */
    /**
     * Add element to the Block
     * To add alement to the bloc the block need to have a lineRemanaining > 0.
     * 
     * @param hash element must be a String result of SHA256() foudable in
     *             {@link serveur.util.security.Hash}
     */
    public void add(String hash) {
        if (remainingLine() > 0) {
            this.elements.add(hash);
        } else {
            // exception
        }
    }

    /**
     * 
     * The block is valid if full and the previous block is valid.
     * The block need to be not null.
     * 
     * @return true if the block is valid.
     */
    public boolean isValid() {
        Block block = new Block(lastHash);
        return getElements().size() == stamp && block != null && block.isValid();
    }

    /**
     *
     * If the block is empty if there are no actions saved in.
     * The action is saved in the elements ArrayList.
     * Any action corresponding to an Hash result of the SHA256() method foudable in
     * {@link serveur.util.security.Hash}
     * 
     * @return true if the block is empty.
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * The size of the element list correspond to the size of the block.
     * 
     * @return the size of the elements liste.
     */
    public int size() {
        return elements.size();
    }

    /**
     * The line remaning of the block correponding of the size of block -
     * Block[stamp] size.
     * 
     * @return the line remaining in the block.
     */
    public int remainingLine() {
        return this.size() - stamp;
    }

    /* Getter and setter method */
    public ArrayList<String> getElements() {
        return elements;
    }

    public Key getLastHash() {
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
        properties.setProperty("lastHash", lastHash.getPrivateKeyString());
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : elements) {
            stringBuilder.append(str);
            stringBuilder.append(" ");
        }
        properties.setProperty("elements", stringBuilder.toString());
        return properties;
    }

    /* Static Method */
    /**
     * Read a block saved in a file :
     * 
     * @param fileName a properties file who respecte the syntax of a Block.
     * @return the block saved in a file. If an error is ocurate the medhod return
     *         {@null}.
     */
    protected static Block readBlock(File fileName) {
        Properties properties = DataProp.read(fileName);
        if (properties != null) {
            Key lastBlock = new Key(Key.PublicKeyfromString((String) properties.get("lastHash")));
            ChainObject chainObject = new ChainObject(fileName);
            return new Block(chainObject.getSignature(), chainObject.getEncryptedSave(), lastBlock,
                    new ArrayList<>(Arrays.asList(properties.getProperty("elements").split(" "))));
        } else {
            return null;
        }
    }

}
