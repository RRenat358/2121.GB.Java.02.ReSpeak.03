package ru.rrenat358.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rrenat358.command.Command;
import ru.rrenat358.command.CommandType;
import ru.rrenat358.command.commands.AuthCommandData;
import ru.rrenat358.command.commands.PrivateMessageCommandData;
import ru.rrenat358.command.commands.PublicMessageCommandData;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

    private String userName;

    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private ServerHandler serverHandler;
    //todo singleton
//    private ServerHandler serverHandler = ServerHandler.getInstance();

    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    public ClientHandler(ServerHandler serverHandler, Socket clientSocket) {
        this.serverHandler = serverHandler; //todo не передовать, заиспользовать из синглтона
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
                logger.error("Error: Thread.readMessage()");
            } finally {
                try {
                    closeClientConnection();
                    logger.error("Error: Thread.closeClientConnection() \n----------");
                } catch (IOException e) {
                    logger.error("Error: clientSocket.close \n----------");
                }
            }
        }).start();
    }

    private void authenticate() throws IOException {
        while (true) {
            Command command = readCommand();

            if (command == null) {
                continue;
            }
            if (command.getType() == CommandType.AUTH) {
                AuthCommandData data = (AuthCommandData) command.getData();

                String login = data.getLogin();
                String password = data.getPassword();

                String userName = this.serverHandler.getAuthService().getUserNameByLogPass(login, password);
                logger.debug("Введено: login = {}, password = {}",login, password);

                if (userName == null) {
                    sendCommand(Command.errorCommand("Некорректные логин/пароль"));
                    logger.warn("Некорректные логин/пароль");
                } else if (serverHandler.isUserNameBusy(userName)) {
                    sendCommand(Command.errorCommand("Такой пользователь уже существует"));
                    logger.warn("Такой пользователь уже существует");
                } else {
                    this.userName = userName;
                    sendCommand(Command.authOkCommand(userName));
                    serverHandler.subscribe(this);
                    logger.info("Авторизован: login = {}, password = {}, getUserName = {}",login, password, getUserName());
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
            logger.error("Failed to read Command class");
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
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String receiver = data.getReceiver();
                    String privateMessage = data.getMessage();
                    serverHandler.sendPrivateMessage(this, receiver, privateMessage);
                    break;
                }
                case PUBLIC_MESSAGE:
                    PublicMessageCommandData data1 = (PublicMessageCommandData) command.getData();
                    processMessage(data1.getMessage());
                    break;
            }
        }
    }

    private void processMessage(String message) throws IOException {
        this.serverHandler.messagePassAll(this, message);
    }

    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
    }

    public void closeClientConnection() throws IOException {
        outputStream.close();
        inputStream.close();
        serverHandler.unsubscribe(this);
        clientSocket.close();
        logger.debug("closeClientConnection() = clientSocket {}, getUserName {}", clientSocket, getUserName());
    }

    public String getUserName() {
        return userName;
    }

}

