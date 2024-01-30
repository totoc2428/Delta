package util.network.p2p;

import java.net.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import chainobject.hardwere.Device;
import util.data.Prop;

public class P2PNetwork {

    private static final Properties PROPERTIES = Prop.read(
            Paths.get(Prop.read(Paths.get("./resources/config/init.conf").toFile()).getProperty("NetworkConfig"))
                    .toFile());
    private static DatagramSocket socket;
    private static ArrayList<String> receivedMessages;
    private static ArrayList<Device> knowDevices;

    private static Thread listenerThread;

    private static final Device HOST = null;

    private static final int OFFSET = Integer.parseInt(PROPERTIES.getProperty("NodeOffset"));
    private static final int PORT = Integer.parseInt(PROPERTIES.getProperty("NodeListeningPort"));
    private static final String HOST_BROADCAST = PROPERTIES.getProperty("hostBroadcastAddress");

    /**
     * Initialize the node. This method setUp the network to be operational. When is
     * call the Network starting to listening and serv.
     */
    public static void initialize() {
        P2PNetwork.receivedMessages = new ArrayList<String>();
        try {
            socket = new DatagramSocket(PORT);
            startListeningThread();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method close the network. When this method is called the node will be
     * down !
     */
    public static void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    /**
     * This method take a message to diffuse it to all the device connected to the
     * nework.
     * 
     * @param message       the message you want to diffuse on the network
     * @param broadcastPort the broadcast PORT you TARGET.
     */
    public static void broadcastMessage(String message, int broadcastPort) {
        sendMessage(message, HOST_BROADCAST, broadcastPort);
    }

    /**
     * This method take a message to send it to a testination adress and port.
     * 
     * @param message            the message you want to send
     * @param destinationAddress the destination address.
     * @param destinationPort    the destination port.
     */
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

    /**
     * This method give the last message arrived in the
     * {@link P2PNetwork#receivedMessages} {@link ArrayList}. When you call this
     * methode the message is removed from the list.
     * 
     * @return the last message or null if the list is empty.
     */
    public static String getLastMessage() {
        String message = null;
        if (!receivedMessages.isEmpty()) {
            message = receivedMessages.get(receivedMessages.size() - 1);
            receivedMessages.remove(receivedMessages.size() - 1);
        }
        return message;
    }

    /**
     * this method manage the listening part of the P2PNetwork. When a message
     * arrived it will be saved in the {@link P2PNetwork#receivedMessages}
     * {@link arrayList}.
     */
    private static void startListeningThread() {
        receivedMessages.clear();
        listenerThread = new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);

                    P2PNetwork.receivedMessages.add(new String(receivePacket.getData(),
                            OFFSET, receivePacket.getLength()));
                }
            } catch (Exception e) {
                e.getMessage();
            }
        });

        listenerThread.start();
    }
}
