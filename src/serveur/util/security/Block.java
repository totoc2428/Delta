package serveur.util.security;

import java.util.ArrayList;

import serveur.util.data.DataCSV;

public class Block {
    private int blockNumber;
    private String lastHash;
    private ArrayList<String> elements;
    private static final int stamp = 100;

    public Block(int blockNumber, String lastHash, ArrayList<String> elements) {
        this.blockNumber = blockNumber;
        this.lastHash = lastHash;
        this.elements = elements;
    }

    public Block(String fileName) {
        this(DataCSV.readBlock(fileName).getBlockNumber(), DataCSV.readBlock(fileName).getLastHash(),
                DataCSV.readBlock(fileName).getElements());
    }

    public Block() {
        blockNumber = 0;
        lastHash = "";
        elements = new ArrayList<String>();
    }

    public int getBlockNumber() {
        return blockNumber;
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
        String b = new Block(this.getBlockNumber() - 1 + ".csv").toString();
        if (this.getLastHash().equals(b) && elements.size() == stamp - 1) {
            return true;
        }

        return false;
    }

}
