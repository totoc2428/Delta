package util.network.p2p;

import java.net.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import util.data.Prop;

public class P2PNetwork {

    private static final Properties PROPERTIES = Prop.read(
            Paths.get(Prop.read(Paths.get("./resources/config/init.conf").toFile()).getProperty("NetworkConfig"))
                    .toFile());
    private static DatagramSocket socket;
    private static ArrayList<String> receivedMessages;
    private static final int PORT = Integer.parseInt(PROPERTIES.getProperty("NodeListeningPort"));
    private static final String HOST_DIFFUSION = PROPERTIES.getProperty("hostDiffisionAddress");

    public static void initialize() {
        P2PNetwork.receivedMessages = new ArrayList<String>();
        try {
            socket = new DatagramSocket(PORT);
            startListeningThread();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private static void startListeningThread() {
        receivedMessages.clear();
        Thread listenerThread = new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);

                    P2PNetwork.receivedMessages.add(new String(receivePacket.getData(), 0, receivePacket.getLength()));
                }
            } catch (Exception e) {
                e.getMessage();
            }
        });

        listenerThread.start();
    }

    public static void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void broadcastMessage(String message, int broadcastPort) {
        try {
            byte[] sendData = message.getBytes();
            InetAddress broadcastAddress = InetAddress.getByName(HOST_DIFFUSION);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcastAddress, broadcastPort);
            socket.setBroadcast(true);
            socket.send(sendPacket);
            socket.setBroadcast(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message, String destinationAddress, int destinationPort) {
        try {
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                    InetAddress.getByName(destinationAddress), destinationPort);
            socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String receiveMessage() {
        try {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            return new String(receivePacket.getData(), 0, receivePacket.getLength());
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String getLastMessage() {
        String message = null;
        if (!receivedMessages.isEmpty()) {
            message = receivedMessages.get(receivedMessages.size() - 1);
            receivedMessages.remove(receivedMessages.size() - 1);
        }
        return message;
    }

}
