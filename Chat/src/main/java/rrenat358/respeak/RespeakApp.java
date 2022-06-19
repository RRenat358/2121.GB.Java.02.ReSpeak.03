package rrenat358.respeak;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rrenat358.respeak.controllers.AuthController;
import rrenat358.respeak.controllers.RespeakController;
import rrenat358.respeak.model.Network;

import java.io.IOException;

//
//lesson08. 00:19:00 == Dialogs
//lesson08. 00:28:00 == Dialogs --> AuthController
// lesson08. 00:29:00 == isConnectedToServer
// lesson08. 00:35:00 == window Switch
// lesson08. 00:40:00 == обработчики
// lesson08. 00:57:00 == initViews
// lesson08. 01:01:00 == users
// lesson08. 01:03:00 == MyServer
// lesson08. 01:22:00 == перерывКонец
// lesson08. 01:24:00 == commands реализация
// lesson08. 01:29:00 == commands использование
// lesson08. 01:34:00 == commands использование 2
// lesson08. 01:50:00 == Network
// lesson08. 01:54:00 == Network sendCommand
// lesson08. 01:57:00 == **Controller
// lesson08. 01:59:00 == *
// lesson08. 02:03:00 == AuthController
// lesson08. 02:04:00 == Network
// lesson08. 02:08:00 == *
// lesson08. 02:08:50 == run App → fail
// lesson08. 02:10:20 == run App → ok
// lesson08. 02:16:28 == дальше
// lesson08. 02:16:52 == showError
// lesson08. 02:17:40 == notifyUserListUpdated()
// lesson08. 02:23:00 == *
// lesson08. 02:38:00 == *
// lesson08. 02:57:00 == Runnable and OK
// lesson08. 02:57:40 == Рефакторинг
// Runnable
//

public class RespeakApp extends Application {

    private static final String nameApp = "reSpeak!";

    private Stage chatStage;
    private Stage authStage;
    private FXMLLoader chatWindowLoader;
    private FXMLLoader authWindowLoader;

    //    private RespeakController respeakController = RespeakController.getInstance();
    private RespeakController respeakController;
    private Network network = Network.getInstance();
    private static RespeakApp INSTANCE;


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.chatStage = primaryStage;

        initViews();
        getAuthStage().show();
        getAuthController().initializeMessageHandlerAuthController();
    }

    private void initViews() throws IOException {
        initAuthWindow();
        initChatWindow();
    }

    //============================================================
    private void initChatWindow() throws IOException {
        chatWindowLoader = new FXMLLoader();
        chatWindowLoader.setLocation(RespeakApp.class.getResource("respeak-view.fxml"));
        Parent chatWindowPanel = chatWindowLoader.load();
        chatStage.setScene(new Scene(chatWindowPanel, 640, 480));

        chatStage.setResizable(false);
        chatStage.setTitle(nameApp);
        chatStage.getIcons().add(new Image("file:Chat/src/main/images/icons/ReSpeak-Blue-96(-xxxhdpi).png"));
        getRespeakController().controllerSetting();

        getRespeakController().initializeMessageHandlerChatController();

        chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
            }
        });

    }

    //============================================================
    private void initAuthWindow() throws IOException {
        authWindowLoader = new FXMLLoader();
        authWindowLoader.setLocation(RespeakApp.class.getResource("authorization.fxml"));
        AnchorPane authWindowPanel = authWindowLoader.load();
        authStage = new Stage();
        authStage.setScene(new Scene(authWindowPanel));

        authStage.setResizable(false);
        authStage.setTitle(nameApp + " --> Авторизация");
        authStage.getIcons().add(new Image("file:Chat/src/main/images/icons/ReSpeak-Blue-96(-xxxhdpi).png"));

        authStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
            }
        });
    }

    //============================================================
    public void switchToChatWindow(String userName) {
        getAuthController().close();
        getAuthStage().close();

        getChatStage().show();
        getChatStage().setTitle(nameApp + " --> " + userName);
    }

    //============================================================
    public static void main(String[] args) {
        launch();
    }

    public Stage getChatStage() {
        return chatStage;
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public RespeakController getRespeakController() {
        return chatWindowLoader.getController();
    }

    public AuthController getAuthController() {
        return authWindowLoader.getController();
    }

/*
    private static class SingletonHelper {
        private static final RespeakApp INSTANCE = new RespeakApp();
    }

    public static RespeakApp getInstance() {
        return SingletonHelper.INSTANCE;
    }
*/

    @Override
    public void init() {
        INSTANCE = this;
    }

    public static RespeakApp getInstance() {
        return INSTANCE;
    }


}