package rrenat358.respeak.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rrenat358.respeak.RespeakApp;
import rrenat358.respeak.dialogs.DialogEnum;
import rrenat358.respeak.model.Network;
import rrenat358.respeak.model.ReadMessageListener;
import rrenat358.respeak.model.TimerAuthNetworkConnect;
import ru.rrenat358.command.Command;
import ru.rrenat358.command.CommandType;
import ru.rrenat358.command.commands.AuthOkCommandData;

import java.io.IOException;


public class AuthController {

    @FXML
    public PasswordField passwordField;
    @FXML
    public Button authButton;
    @FXML
    public TextField loginField;

    private String login;
    private String password;
    private String userName;



    private RespeakApp respeakApp = RespeakApp.getInstance();
    private Network network = Network.getInstance();
    public ReadMessageListener readMessageListener;
    private TimerAuthNetworkConnect timerAuthNetworkConnect = TimerAuthNetworkConnect.getInstance();

    public AuthController() {
    }

    public AuthController(String login, String password, String userName) {
        this.login = login;
        this.password = password;
        this.userName = userName;
    }


    @FXML
    public void executeAuth() {
        this.login = loginField.getText();
        this.password = passwordField.getText();

        if (login == null || password == null || login.isBlank() || password.isBlank()) {
            DialogEnum.AuthError.LOGOPASS_EMPTY.show();
            return;
        }

        if (timerAuthNetworkConnect.timeOff()) {
            DialogEnum.NetworkError.SERVER_CONNECT.show();
            return;
        }

        if (!isConnectedToServer()) {
            DialogEnum.NetworkError.SERVER_CONNECT.show();
        }

        try {
            network.sendAuthMessage(login, password);

        } catch (IOException e) {
            DialogEnum.NetworkError.SEND_MESSAGE.show();
            e.printStackTrace();
        }
    }


    public void initializeMessageHandlerAuthController() {
        readMessageListener = network.addReadMessageListner(new ReadMessageListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.AUTH_OK) {
                    AuthOkCommandData data = (AuthOkCommandData) command.getData();
                    String userName = data.getUserName();
                    Platform.runLater(() -> {
                        respeakApp.switchToChatWindow(userName);
                    });
                } else {
                    Platform.runLater(() -> {
                        DialogEnum.AuthError.LOGOPASS_INVALID.show();
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


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    private static class SingletonHelper {
        private static final AuthController INSTANCE = new AuthController();
    }

    public static AuthController getInstance() {
        return SingletonHelper.INSTANCE;
    }


}
