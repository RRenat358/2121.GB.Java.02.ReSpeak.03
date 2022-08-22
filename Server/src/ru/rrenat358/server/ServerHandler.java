package ru.rrenat358.server;

import ru.rrenat358.command.Command;
import ru.rrenat358.server.auth.AuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler {

    private static AuthService authService;
//    private AuthService authService = AuthService.getInstance();

    private final List<ClientHandler> clientList = new ArrayList<>();

    public void serverStart(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server has been started");
            authService = new AuthService();
            while (true) {
                waitClientConnection(serverSocket);
            }
        } catch (IOException e) {
            System.err.println("Server NO started. PORT: " + port + "\n----------");
        }
    }

    private void waitClientConnection(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Waiting for new client connection" + "\n----------");

        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.startClientHandle();
        System.out.println("Client has been connected");
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
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clientList.add(clientHandler);
        System.out.println("New User subscribe -- " + clientHandler.getUserName());
        notifyUserListUpdated();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clientList.remove(clientHandler);
        notifyUserListUpdated();
    }
    //synchronized
    public synchronized AuthService getAuthService() {
        return authService;
    }

    private static class SingletonHelper {
        private static final ServerHandler INSTANCE = new ServerHandler();
    }

    public static ServerHandler getInstance() {
        return SingletonHelper.INSTANCE;
    }

}
