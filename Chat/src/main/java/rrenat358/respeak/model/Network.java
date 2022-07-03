package rrenat358.respeak.model;

import ru.rrenat358.command.Command;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Network {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8358;

    private List<ReadMessageListener> listners = new CopyOnWriteArrayList<>();

    private String host;
    private int port;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private Thread readMessageProcess;
    private boolean connected;

    public Network() {
        this(SERVER_HOST, SERVER_PORT);
    }

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream()); //"запись" - в исходящий поток
            inputStream = new ObjectInputStream(socket.getInputStream()); //"чтение" - из входящего потока
            readMessageProcess = startReadMessageProcess();

            connected = true;
            return true;
        } catch (IOException e) {
            System.err.println("Network.connect()" + "\n----------");
            e.printStackTrace();
            return false;
        }
    }

    public void sendPrivateMessage(String receiver, String message) throws IOException {
        sendCommand(Command.privateMessageCommand(receiver, message));
    }

    public void sendCommand(Command command) throws IOException {
        try {
            outputStream.writeObject(command);
        } catch (IOException e) {
            System.err.println("Сообщение не отправлено на сервер" + "\n----------");
            e.printStackTrace();
            throw e;
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

    public void sendMessage(String message) throws IOException {
        sendCommand(Command.publicMessageCommand(message));
    }

    public void sendAuthMessage(String login, String password) throws IOException {
        sendCommand(Command.authCommand(login, password));
    }

    public Thread startReadMessageProcess() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (Thread.currentThread().isInterrupted()) {
                            return;
                        }
                        Command command = readCommand();
                        for (ReadMessageListener listner : listners) {
                            listner.processReceivedCommand(command);
                        }

                    } catch (IOException e) {
                        System.err.println("Сообщение не получено от сервера" + "\n----------");
                        e.printStackTrace();
                        socketClose();
                        break;
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    public ReadMessageListener addReadMessageListner(ReadMessageListener listener) {
        this.listners.add(listener);
        return listener;
    }

    public void removeReadMessageListner(ReadMessageListener listener) {
        this.listners.remove(listener);
    }

    public void socketClose() {
        try {
            connected = false;
            socket.close();
            readMessageProcess.interrupt();
        } catch (IOException e) {
            System.err.println("Не удалось закрыть сетевое соединение" + "\n----------");
        }
    }

    public boolean isConnected() {
        return connected;
    }

    private static class SingletonHelper {
        private static final Network INSTANCE = new Network();
    }

    public static Network getInstance() {
        return SingletonHelper.INSTANCE;
    }

}