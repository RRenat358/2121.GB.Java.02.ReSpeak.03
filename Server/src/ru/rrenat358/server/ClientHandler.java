package ru.rrenat358.server;


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
                System.err.println("Error: messageRead" + "\n----------");
                e.printStackTrace();
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

            if (command == null) {
                continue;
            }

            if (command.getType() == CommandType.AUTH) {
                AuthCommandData data = (AuthCommandData) command.getData();

                String login = data.getLogin();
                String password = data.getPassword();

                String userName = this.serverHandler.getAuthService().getUserNameByLoginPassword(login, password);

                if (userName == null) {
                    sendCommand(Command.errorCommand("Некорректные логин/пароль"));
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
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String receiver = data.getReceiver();
                    String privateMessage = data.getMessage();
                    serverHandler.sendPrivateMessage(this, receiver, privateMessage);
                    break;
                }
                case PUBLIC_MESSAGE:
                    PublicMessageCommandData data1 = (PublicMessageCommandData) command.getData();
/*
                    String clientMessage = data1.getMessage();

                    // miniMessage == "/w u m" == 6 simbol
                    if (clientMessage.length() >= 6) {
                        String[] clientMessageSlit = clientMessage.split(" ");
                        if (clientMessageSlit.length >= 3 && clientMessageSlit[0].length() == 2) {

                            PrivateMessageCommandData data3 = (PrivateMessageCommandData) command.getData();
                            String receiver = data3.getReceiver();
                            String privateMessage = data3.getMessage();
                            serverHandler.sendPrivateMessage(this, receiver, privateMessage);


                            System.out.println("Command send: /w");
//                            processMessage("data1 == /w");
                            break;

                        }
                    }

*/
/*                    if (data1.getMessage().equals("/w")) {
                        System.out.println("Command send: /w");
                        processMessage("data1 == /w");
                        break;
                    }*//*


*/

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
    }

    public String getUserName() {
        return userName;
    }

}

