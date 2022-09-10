package ru.rrenat358.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rrenat358.command.Command;
import ru.rrenat358.dbconnect.DBConnect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler {

    private static AuthService authService;
//    private AuthService authService = AuthService.getInstance();

    private static final Logger logger = LogManager.getLogger(AuthService.class);
    private final List<ClientHandler> clientList = new ArrayList<>();

    public void serverStart(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port);
             DBConnect dbConnect =  new DBConnect())
        {
            authService = new AuthService();
            logger.info("Server has been started");
            System.out.println("Server has been started");

            dbConnect.connect();
            logger.info("Server connection to DB");
            System.out.println("Server connection to DB");

            while (true) {
                waitClientConnection(serverSocket);
            }
        } catch (IOException ex) {
            logger.error("Server NO started. PORT: {} \n----------",port);
            System.err.println("Server NO started. PORT: " + port + "\n----------");
            ex.printStackTrace();
        }
    }

    private void waitClientConnection(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        logger.info("Waiting for new client connection \n----------");
        System.out.println("Waiting for new client connection \n----------");

        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.startClientHandle();
        logger.info("Client has been connected = {} clientSocket", clientSocket);
        System.out.println("Client has been connected =");
    }

    public synchronized void messagePassAll(ClientHandler sender, String message) throws IOException {
        for (ClientHandler client : clientList) {
            if (client != sender) {
                client.sendCommand(Command.clientMessageCommand(sender.getUserName(), message));
            }
        }
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {
        for (ClientHandler client : clientList) {
            if (client != sender && client.getUserName().equals(recipient)) {
                client.sendCommand(Command.privateMessageCommand(sender.getUserName(), privateMessage));
            }
        }
    }

    private synchronized void notifyUserListUpdated() throws IOException {
        List<String> users = new /*CopyOnWrite*/ArrayList<>();
        for (ClientHandler client : clientList) {
            users.add(client.getUserName());
        }
        for (ClientHandler client : clientList) {
            client.sendCommand(Command.updateUserListCommand(users));
        }

    }

    public synchronized boolean isUserNameBusy(String userName) {
        for (ClientHandler client : clientList) {
            if (client.getUserName().equals(userName)) {
                logger.error("isUserNameBusy() = true");
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clientList.add(clientHandler);
        logger.info("New User subscribe = {} \n--------------------", clientHandler.getUserName());
        System.out.println("New User subscribe = " + clientHandler.getUserName() + "\n--------------------");
        notifyUserListUpdated();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clientList.remove(clientHandler);
        notifyUserListUpdated();
    }

    public AuthService getAuthService() {
        return authService;
    }

    private static class SingletonHelper {
        private static final ServerHandler INSTANCE = new ServerHandler();
    }

    public static ServerHandler getInstance() {
        return SingletonHelper.INSTANCE;
    }

}
