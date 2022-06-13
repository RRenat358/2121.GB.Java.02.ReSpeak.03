package rrenat358.respeak.dialogs;

import javafx.scene.control.Alert;
import rrenat358.respeak.RespeakApp;


public class DialogEnum {

    private static RespeakApp respeakApp = RespeakApp.getInstance();

    public enum AuthError {
        LOGOPASS_EMPTY("Логин или пароль не могут быть пустыми"),
        LOGOPASS_INVALID("""
                RespeakApp:\s
                Неверные логин/пароль.\s
                Попробуйте ещё раз."""
        );

        private static final String TITLE = "Ошибка аутентификации";
        private static final String TYPE = TITLE;

        private final String message;

        AuthError(String message) {
            this.message = message;
        }

        public void show() {
            showDialog(Alert.AlertType.ERROR, TITLE, TITLE, message);
        }
    }


    public enum NetworkError {
        SERVER_CONNECT("Нет соединения с сервером"),
        SEND_MESSAGE("Сообщение не отправлено");

        private static final String TITLE = "Ошибка сети";
        private static final String TYPE = TITLE;

        private final String message;

        NetworkError(String message) {
            this.message = message;
        }

        public void show() {
            showDialog(Alert.AlertType.ERROR, TITLE, TITLE, message);
        }
    }


    private static void showDialog(Alert.AlertType dialogType, String title, String type, String message) {
        Alert alert = new Alert(dialogType);
        alert.initOwner(respeakApp.getChatStage()); //todo 00:26
        alert.setTitle(title);
        alert.setHeaderText(type);

        alert.setContentText(message);
        alert.showAndWait();
    }


}
