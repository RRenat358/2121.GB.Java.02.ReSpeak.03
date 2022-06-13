package rrenat358.respeak.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rrenat358.respeak.RespeakApp;
import rrenat358.respeak.dialogs.Dialog;
import rrenat358.respeak.model.Network;
import rrenat358.respeak.model.ReadMessageListener;
import ru.rrenat358.command.Command;
import ru.rrenat358.command.CommandType;
import ru.rrenat358.command.commands.AuthOkCommandData;

import java.io.IOException;


public class AuthController {
//    public static final String AUTH_COMMAND = "/auth";
//    public static final String AUTH_COMMAND_OK = "/authOK";

    @FXML
    public PasswordField passwordField;
    @FXML
    public Button authButton;
    @FXML
    public TextField loginField;

    private RespeakApp respeakApp = RespeakApp.getInstance();
    private Network network = Network.getInstance();
    public ReadMessageListener readMessageListener;

    @FXML
    public void executeAuth(/*ActionEvent actionEvent*/) {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login == null || password == null || login.isBlank() || password.isBlank()) {
//            respeakApp.alertErrorDialog("Логин и пароль должны быть заполнены");
            Dialog.AuthError.LOGOPASS_EMPTY.show();
            return;
        }

        if (!isConnectedToServer()) {
            Dialog.NetworkError.SERVER_CONNECT.show();
        }

        try {
            network.sendAuthMessage(login, password);

        } catch (IOException e) {
            Dialog.NetworkError.MESSAGE_SEND.show();
//            respeakApp.alertErrorDialog("Ошибка передачи данных по сети");
        }
    }


    public void initializeMessageHandlerAuthContrller() {
        readMessageListener = network.addReadMessageListner(new ReadMessageListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.AUTH_OK) {
                    AuthOkCommandData data = (AuthOkCommandData) command.getData();
                    String userName = data.getUserName();
                    Platform.runLater(() -> {
                        respeakApp.getChatStage().setTitle(respeakApp.getChatStage().getTitle() + " --> " + userName);
                        respeakApp.switchToChatWindow(userName);
                    });
                } else {
                    Platform.runLater(() -> {
                        Dialog.AuthError.LOGOPASS_INVALID.show();
                    });
                }
            }
        });
    }


    public boolean isConnectedToServer() {
        return network.isConnected() || network.connect();
    }


    public void close() {
        network.removeReadMessageListner(readMessageListener);
    }
}