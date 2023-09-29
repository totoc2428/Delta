package serveur.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import serveur.util.security.Key;

public class Node {

    private String ipAddress;
    private int port;
    private List<Node> neighbors;
    private Key signature;

    public Node(String ipAddress, int port, Key signature) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.neighbors = new ArrayList<>();
        this.signature = signature;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
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

    public void listenForMessages() {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Key getSignature() {
        return signature;
    }
}
