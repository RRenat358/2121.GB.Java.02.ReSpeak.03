package rrenat358.respeak.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rrenat358.respeak.RespeakApp;
import rrenat358.respeak.dialogs.DialogEnum;
import rrenat358.respeak.model.Network;
import rrenat358.respeak.model.ReadMessageListener;
import ru.rrenat358.command.Command;
import ru.rrenat358.command.CommandType;
import ru.rrenat358.command.commands.ClientMessageCommandData;
import ru.rrenat358.command.commands.UpdateUserListCommandData;

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


    private RespeakApp respeakApp = RespeakApp.getInstance();
    private Network network = Network.getInstance();

    public void sendMessage() {
        String message = messageTextField.getText().trim();
        if (message.isEmpty()) {
//            messageInputRequestFocus();
            messageTextField.clear();
            return;
        }

        String senderThis = null;
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
            System.err.println("err: RespeakController.sendMessage()");
            DialogEnum.NetworkError.SEND_MESSAGE.show();
//            respeakApp.alertErrorDialog("Ошибка передачи данных по сети");
        }

        messageSendToBox("Я", message);
//        Уместнее здесь
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

    void messageInputRequestFocus() {
        Platform.runLater(() ->
                messageTextField.requestFocus()
        );
    }

    public void initializeMessageHandlerChatController() {
        network.addReadMessageListner(new ReadMessageListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.CLIENT_MESSAGE) {
                    ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                    messageSendToBox(data.getSender(), data.getMessage());
                } else if (command.getType() == CommandType.UPDATE_USERS_LIST) {
                    UpdateUserListCommandData data = (UpdateUserListCommandData) command.getData();
                    Platform.runLater(() -> {
                        userListing.setItems(FXCollections.observableArrayList(data.getUsers()));
                    });
                }
            }
        });
    }

    //todo -- iconUser, iconSmileToMessage
    //todo -- Menu/Exit*menu.window().close()
    public void controllerSetting() {
        userListing.getSelectionModel().selectFirst(); //todo -- this does not work
        messageBox.setWrapText(true);
        messageBox.setEditable(false);
        messageBox.setStyle("-fx-font-size: 8px;"
                /* + "-fx-background-color: red;"*/
        );
        messageBox.setBorder(null); //todo -- this does not work
        messageButtonInput.disableProperty().bind(messageTextField.textProperty().isEmpty());
        messageInputRequestFocus();
    }


    private static class SingletonHelper {
        private static final RespeakController INSTANCE = new RespeakController();
    }

    public static RespeakController getInstance() {
        return SingletonHelper.INSTANCE;
    }
}