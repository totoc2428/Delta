package serveur.network;

import java.util.ArrayList;

public class Network {
    ArrayList<Node> nodeList;

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public int size() {
        return nodeList.size();
    }
}
