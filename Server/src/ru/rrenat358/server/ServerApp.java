package ru.rrenat358.server;

public class ServerApp {

    private static final int PORT = 8358;

    public static void main(String[] args) {
        new ServerHandler().serverStart(PORT);

        //todo стоит ли закрывать порт когда приложение завершает работу?
        //например:
//        new ClientHandler().closeConnection();
    }

}
