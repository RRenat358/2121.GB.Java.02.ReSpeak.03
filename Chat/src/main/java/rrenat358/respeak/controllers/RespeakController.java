package rrenat358.respeak.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import rrenat358.respeak.RespeakApp;
import rrenat358.respeak.model.Network;
import rrenat358.respeak.model.ReadMessageListener;
import ru.rrenat358.command.Command;
import ru.rrenat358.command.CommandType;
import ru.rrenat358.command.commands.ClientMessageCommandData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class RespeakController {

    @FXML
    public Button messageButtonInput;
    @FXML
    public TextField messageTextField;
    //todo -- перейти на многострочный ввод TextArea
    //public TextArea messageTextArea;
    @FXML
    public TextArea messageBox;
    @FXML
    public ListView userListing;
    @FXML
    public ListView messageBoxHeader;

    private String senderThis = null;

    private static final List<String> USER_TEST_DATA = List.of(
            "1",
            "2",
            "3"
    );

    @FXML
    public void initialize() {
        userListing.setItems(FXCollections.observableArrayList(USER_TEST_DATA));
    }

    //    private RespeakApp respeakApp = RespeakApp.getInstance();
    private RespeakApp respeakApp;
    private Network network = Network.getInstance();

    public void sendMessage() {
        String message = messageTextField.getText().trim();
        if (message.isEmpty()) {
            messageInputRequestFocus();
            return;
        }
//        this.selectedUserName = userListing.getSelectionModel().getSelectedItem().toString();
        if (!userListing.getSelectionModel().isEmpty()) {
            senderThis = userListing.getSelectionModel().getSelectedItem().toString();
        }

        try {
            if (senderThis != null) {
                network.sendPrivateMessage(senderThis, message);
            } else {
                network.sendMessage(message);
            }
//            message = selectedUserName + ": \n" + message;

        } catch (IOException e) {
            System.err.println("err: RespeakController.messageSendController()");
            respeakApp.alertErrorDialog("Ошибка передачи данных по сети");
        }

        messageSendToBox("Я", message);
//        messageTextField.clear();
//        messageInputRequestFocus();
    }

    public void messageSendToBox(String selectedUserName, String message) {
        //todo↓ -- ?this does not work
//        this.selectedUserName = userListing.getSelectionModel().getSelectedItem().toString();
        //todo *.getItems().addAll(userName)
        messageBox.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                "yyyy.MM.dd-HH:mm:ss" + "   |   "
        )));
        if (selectedUserName != null) {
            messageBox.appendText(selectedUserName);
            messageBox.appendText(System.lineSeparator());
        }
        messageBox.appendText(message);
        messageBox.appendText(System.lineSeparator());
        messageBox.appendText("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
        messageBox.appendText(System.lineSeparator());

        messageInputRequestFocus();
        messageTextField.clear();
    }

    public void initializeMessageHandlerChatController() {
        network.addReadMessageListner(new ReadMessageListener() {
            @Override
            public void processReceivedCommand(Command command) {

                if (command.getType() == CommandType.CLIENT_MESSAGE) {
                    ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                    messageSendToBox(data.getSender(), data.getMessage());
                }
            }
        });
    }


    //todo -- iconUser, iconSmileToMessage
    //todo -- Menu/Exit*menu.window().close()

    public void controllerSetting() {
        userListing.getSelectionModel().selectFirst();
        messageBox.setWrapText(true);
        messageBox.setEditable(false);
        messageBox.setStyle("-fx-font-size: 10px;"
                /* + "-fx-background-color: red;"*/
        );
        messageBox.setBorder(null); //todo -- ?this does not work
        messageButtonInput.disableProperty().bind(messageTextField.textProperty().isEmpty());
        messageInputRequestFocus();
    }

    void messageInputRequestFocus() {
        Platform.runLater(() ->
                messageTextField.requestFocus()
        );
    }

    private static class SingletonHelper {
        private static final RespeakController INSTANCE = new RespeakController();
    }

    public static RespeakController getInstance() {
        return SingletonHelper.INSTANCE;
    }
}