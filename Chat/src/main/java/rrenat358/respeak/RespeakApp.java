package rrenat358.respeak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rrenat358.respeak.controllers.AuthController;
import rrenat358.respeak.controllers.RespeakController;
import rrenat358.respeak.model.Network;

import java.io.IOException;


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


//todo lesson08. 02:10:20 == пауза
//Runnable


public class RespeakApp extends Application {

    private Stage chatStage;
    private Stage authStage;
    private FXMLLoader chatWindowLoader;
    private FXMLLoader authWindowLoader;

    private RespeakController respeakController = RespeakController.getInstance();
    private Network network = Network.getInstance();


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.chatStage = primaryStage;

        initViews();
        getChatStage().show();
        getAuthStage().show();
        getAuthController().initializeMessageHandlerAuthContrller();

/*
        RespeakController respeakController = createChatWindow(primaryStage);
        connectToServer(respeakController);
        createAuthWindow(primaryStage);

        respeakController.initializeMessageHandlerRespeakController();
*/
    }

    private void initViews() throws IOException {
        initChatWindow();
        initAuthWindow();
    }

    //============================================================
    private void initChatWindow() throws IOException {
        chatWindowLoader = new FXMLLoader();
        chatWindowLoader.setLocation(RespeakApp.class.getResource("respeak-view.fxml"));

        Parent root = chatWindowLoader.load();
        chatStage.setScene(new Scene(root, 640, 480));

        chatStage.setResizable(false);
        chatStage.setTitle("reSpeak!");
        chatStage.getIcons().add(new Image("file:src/main/images/icons/ReSpeak-Blue-96(-xxxhdpi).png"));
//        respeakController.controllerSetting();

        getRespeakController().initializeMessageHandlerRespeakController();
    }

/*
    private RespeakController createChatWindow(Stage stageChat) throws IOException {
//        FXMLLoader chatFxmlLoader = new FXMLLoader();
//        chatFxmlLoader.setLocation(RespeakApp.class.getResource("respeak-view.fxml"));
//        Parent stageChatRoot = chatFxmlLoader.load();
//        Scene chatScene = new Scene(stageChatRoot, 640, 480);

//        this.chatStage.setResizable(false);
//        this.chatStage.setTitle("reSpeak!");
//        this.chatStage.getIcons().add(new Image("file:src/main/images/icons/ReSpeak-Blue-96(-xxxhdpi).png"));
//        this.chatStage.setScene(chatScene);

//        respeakController = chatFxmlLoader.getController();
        RespeakController respeakController = chatFxmlLoader.getController();
        //todo -- не добавлять в список того кто не авторизовался
        respeakController.userListing.getItems().addAll("User01", "User02", "User03");
//        respeakController.controllerSetting();

        stageChat.show();
        return respeakController;
    }
*/

    //============================================================
    private void initAuthWindow() throws IOException {
        authWindowLoader = new FXMLLoader();
        authWindowLoader.setLocation(RespeakApp.class.getResource("authorization.fxml"));
        AnchorPane authWindowPanel = authWindowLoader.load();

        authStage = new Stage();
        authStage.initOwner(chatStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setScene(new Scene(authWindowPanel));

        authStage.setResizable(false);
        authStage.setTitle("reSpeak! --> Авторизация");
        authStage.getIcons().add(new Image("file:src/main/images/icons/ReSpeak-Blue-96(-xxxhdpi).png"));

    }

/*
    private void createAuthWindow(Stage stageChat) throws IOException {
//        FXMLLoader authFxmlLoader = new FXMLLoader();
//        authFxmlLoader.setLocation(RespeakApp.class.getResource("authorization.fxml"));
//        AnchorPane authLoaderRoot = authFxmlLoader.load();
//        Scene sceneAuth = new Scene(authLoaderRoot, 640, 480);

//        authStage = new Stage();
//        authStage.initOwner(stageChat);
//        authStage.initModality(Modality.WINDOW_MODAL);
//        authStage.setResizable(false);
//        authStage.setTitle("reSpeak! --> Авторизация");
//        authStage.getIcons().add(new Image("file:src/main/images/icons/ReSpeak-Blue-96(-xxxhdpi).png"));

//        stageAuth.setScene(sceneAuth);
//        authStage.setScene(new Scene(authLoaderRoot));

        AuthController authController = authFxmlLoader.getController();
        authController.setRespeakApp(this);
        authController.initializeMessageHandlerAuthContrller();

        authStage.showAndWait();
    }
*/

    //============================================================
/*
    private void connectToServer(RespeakController respeakController) {
        boolean resultConnectedToServer = network.connect();
        if (!resultConnectedToServer) {
            String errorMessage = "Нет соединения с сервером \n";
            System.err.println(errorMessage + "\n");
            alertErrorDialog(errorMessage);
        }
        respeakController.setRespeakApp(this);

        chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                network.socketClientClose();
            }
        });
    }
*/

    public void switchToChatWindow(String userName) {
        getChatStage().setTitle(userName);
        getAuthController().close();
        getAuthStage().close();
        getRespeakController().initializeMessageHandlerRespeakController();
    }

    //============================================================
    public void alertErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
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

    private static class SingletonHelper {
        private static final RespeakApp INSTANCE = new RespeakApp();
    }

    public static RespeakApp getInstance() {
        return SingletonHelper.INSTANCE;
    }

}