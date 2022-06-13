package ru.rrenat358.server;


import ru.rrenat358.command.Command;
import ru.rrenat358.command.CommandType;
import ru.rrenat358.command.commands.AuthCommandData;
import ru.rrenat358.command.commands.PrivateMessageCommandData;
import ru.rrenat358.command.commands.PublicMessageCommandData;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

    public static final String AUTH_COMMAND = "/ru/rrenat358/server";
    public static final String AUTH_COMMAND_OK = "/authOK";


    private String userName;

    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private ServerHandler serverHandler = ServerHandler.getInstance();
//    private AuthService authService = AuthService.getInstance();


    public ClientHandler(ServerHandler serverHandler, Socket clientSocket) {
        this.serverHandler = serverHandler;
        this.clientSocket = clientSocket;
    }

    public void startClientHandle() throws IOException {
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        new Thread(() -> {
            try {
                authenticate();
                readMessage();
            } catch (IOException e) {
                System.err.println("Error: messageRead" + "\n----------");
            } finally {
                try {
                    closeClientConnection();
                    System.err.println("closeClientConnection()" + "\n----------");
                } catch (IOException e) {
                    System.err.println("Error: clientSocket.close" + "\n----------");
                }
            }
        }).start();
    }

    private void authenticate() throws IOException {
        while (true) {
            Command command = readCommand();
//            String message = inputStream.readUTF();

            if (command == null) {
                continue;
            }

            if (command.getType() == CommandType.AUTH) {
                AuthCommandData data = (AuthCommandData) command.getData();

                String login = data.getLogin();
                String password = data.getPassword();

                String userName = this.serverHandler.getAuthService().getUserNameByLoginPassword(login, password);
//                String userName = authService.getUserNameByLoginPassword(login, password); //если так -- ошибки. почему?

                if (userName == null) {
                    sendCommand(Command.errorCommand("Некорректные логин/пароль"));
//                    messageSend("Неверные логин/пароль. \n" + "Попробуйте ещё раз)");
                } else if (serverHandler.isUserNameBusy(userName)) {
                    sendCommand(Command.errorCommand("Такой пользователь уже существует"));
                } else {
                    this.userName = userName;
                    sendCommand(Command.authOkCommand(userName));
                    serverHandler.subscribe(this);
                    return;
                }
            }
        }
    }

    private Command readCommand() throws IOException {
        Command command = null;
        try {
            command = (Command) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to read Command class");
            e.printStackTrace();
        }
        return command;
    }

    private void readMessage() throws IOException {
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }

            switch (command.getType()) {
                case PRIVATE_MESSAGE:
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String receiver = data.getReceiver();
                    String privateMessage = data.getMessage();
                    serverHandler.sendPrivateMessage(this, receiver, privateMessage);
                    break;
                case PUBLIC_MESSAGE:
                    PublicMessageCommandData data1 = (PublicMessageCommandData) command.getData();
                    processMessage(data1.getMessage());
                    break;
            }

/*
            String message = inputStream.readUTF().trim();
            System.out.println("message = " + message);
            if (message.startsWith("/end")) {
                return;
            } else {
                processMessage(message);
            }
*/

        }
    }

    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
    }

    private void processMessage(String message) throws IOException {
        this.serverHandler.messagePassAll(this, message);
    }

    public void closeClientConnection() throws IOException {
        outputStream.close();
        inputStream.close();
        serverHandler.unsubscribe(this);
        clientSocket.close();
    }

    public String getUserName() {
        return userName;
    }

}

