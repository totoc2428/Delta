package serveur.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Node {

    private String ipAddress;
    private int port;
    private List<Node> neighbors;

    public Node(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.neighbors = new ArrayList<>();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Node neighbor) {
        this.neighbors.add(neighbor);
    }

    public void removeNeighbor(Node neighbor) {
        this.neighbors.remove(neighbor);
    }

    public void sendMessage(String message, Node destination) throws IOException {
        Socket socket = new Socket(destination.getIpAddress(), destination.getPort());
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes());
        outputStream.flush();
        socket.close();
    }

    public void listenForMessages() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                String message = new String(bytes);
                System.out.println("Received message: " + message);
                socket.close();
            }
        }

    }

}
