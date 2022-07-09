package rrenat358.respeak;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rrenat358.respeak.controllers.AuthController;
import rrenat358.respeak.controllers.RespeakController;
import rrenat358.respeak.model.TimerAuthNetworkConnect;

import java.io.IOException;


public class RespeakApp extends Application {

    private static final String nameApp = "reSpeak!";

    private Stage chatStage;
    private Stage authStage;
    private FXMLLoader chatWindowLoader;
    private FXMLLoader authWindowLoader;

    private static RespeakApp INSTANCE;
    private TimerAuthNetworkConnect timerAuthNetworkConnect = TimerAuthNetworkConnect.getInstance();


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.chatStage = primaryStage;

        initViews();
        getAuthStage().show();
        timerAuthNetworkConnect.authTimeOff();
        getAuthController().initializeMessageHandlerAuthController();
//        timerAuthNetworkConnect.authTaskConnect();

    }

    private void initViews() throws IOException {
        initAuthWindow();
        initChatWindow();
    }

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

    public void switchToChatWindow(String userName) {
        getAuthController().close();
        getAuthStage().close();

        getChatStage().show();
        getChatStage().setTitle(nameApp + " --> " + userName);
    }

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


    @Override
    public void init() {
        INSTANCE = this;
    }

    public static RespeakApp getInstance() {
        return INSTANCE;
    }


}