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
import java.util.concurrent.CopyOnWriteArrayList;

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

            dbConnect.connect();
            logger.info("Server connection to DB");

            while (true) {
                waitClientConnection(serverSocket);
            }
        } catch (IOException ex) {
            logger.error("Server NO started. PORT: {} \n----------",port);
            ex.printStackTrace();
        }
    }

    private void waitClientConnection(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        logger.info("Waiting for new client connection \n----------");

        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.startClientHandle();
        logger.info("Client has been connected = {} clientSocket", clientSocket);
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
        List<String> users = new CopyOnWriteArrayList<>();
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
                logger.warn("isUserNameBusy() = true");
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clientList.add(clientHandler);
        logger.info("New User subscribe = {}", clientHandler.getUserName());
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
