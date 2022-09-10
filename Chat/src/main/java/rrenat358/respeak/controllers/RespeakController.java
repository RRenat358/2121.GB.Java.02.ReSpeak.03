package rrenat358.respeak.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rrenat358.respeak.FileHandler.DataUser;
import rrenat358.respeak.FileHandler.FileIO;
import rrenat358.respeak.RespeakApp;
import rrenat358.respeak.dialogs.DialogEnum;
import rrenat358.respeak.model.Network;
import rrenat358.respeak.model.ReadMessageListener;
import ru.rrenat358.command.Command;
import ru.rrenat358.command.CommandType;
import ru.rrenat358.command.commands.ClientMessageCommandData;
import ru.rrenat358.command.commands.PrivateMessageCommandData;
import ru.rrenat358.command.commands.UpdateUserListCommandData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
    @FXML
    public MenuItem menuExit;

    private String recipient = null;
    private String pathFileMessage = "";

    boolean isReadMessageHistory = false;

    private RespeakApp respeakApp = RespeakApp.getInstance();
    private AuthController authController = AuthController.getInstance();

    private Network network = Network.getInstance();
    private DataUser dataUser = DataUser.getInstance();
    private FileIO fileIO = FileIO.getInstance();



    public void sendMessage() {
        String message = messageTextField.getText().trim();
        if (message.isEmpty()) {
            messageInputRequestFocus();
            return;
        }

        if (!isReadMessageHistory) {
            readMessageHistory(respeakApp.nLineForReadMessageHistory);
            isReadMessageHistory = true;
        }

        pathFileMessage = String.format(
                "%s/%s/%s/%s",
                dataUser.getDataUserDir(), respeakApp.authDataUser.get(0),
                dataUser.getMessDir(), dataUser.getMessFileName()
        );
        fileIO.writeNewLineToFile(pathFileMessage, message);


        recipient = null;
        if (!userListing.getSelectionModel().isEmpty()) {
            recipient = userListing.getSelectionModel().getSelectedItem().toString();
        }

        String[] alternativePrivateMessage = isAlternativePrivateMessage(message);
        if (alternativePrivateMessage != null) {
            recipient = alternativePrivateMessage[0];
            message = alternativePrivateMessage[1];
        }

        try {
            if (recipient != null) {
                network.sendPrivateMessage(recipient, message);
            } else {
                network.sendPublicMessage(message);
            }
        } catch (IOException e) {
            System.err.println("err: ChatController.sendMessage()");
            DialogEnum.NetworkError.SEND_MESSAGE.show();
        }

        messageSendToBox("Я", message);
        messageTextField.clear();
        messageInputRequestFocus();
    }


    public void messageSendToBox(String selectedUserName, String message) {
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

                } else if (command.getType() == CommandType.PRIVATE_MESSAGE) {
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    messageSendToBox(data.getReceiver(), data.getMessage() + "\n [CommandType.PRIVATE_MESSAGE]");

                } else if (command.getType() == CommandType.UPDATE_USERS_LIST) {
                    UpdateUserListCommandData data = (UpdateUserListCommandData) command.getData();
                    Platform.runLater(() -> {
                        userListing.setItems(FXCollections.observableArrayList(data.getUsers()));
                    });
                }
            }
        });
    }

    private String[] isAlternativePrivateMessage(String message) {
        String[] recipientAndMessage = {null, null};
        //search message.length() == "/* u m" == 6 symbol
        if (message.length() >= 6) {
            String[] messageSplit = message.split("\\s+", 3);
            if (messageSplit.length == 3 && messageSplit[0].equals("/*")) {

                for (Object targetRecipient : userListing.getItems()) {

                    if (messageSplit[1].equals(targetRecipient)) {
                        recipientAndMessage[0] = messageSplit[1];
                        recipientAndMessage[1] = messageSplit[2];

                        return recipientAndMessage;
                    }
                }
            }
        }
        return null;
    }

    //todo -- iconUser, iconSmileToMessage
    public void controllerSetting() {
        userListing.getSelectionModel().selectFirst(); //todo -- this does not work
        messageBox.setWrapText(true);
        messageBox.setEditable(false);
        messageBox.setStyle("-fx-font-size: 10px;"
                /* + "-fx-background-color: red;"*/
        );
        messageBox.setBorder(null); //todo -- this does not work
        messageButtonInput.disableProperty().bind(messageTextField.textProperty().isEmpty());
        messageInputRequestFocus();
    }

    public void readMessageHistory(int nLastMessage) {
        String pathFileMessage = String.format(
                "%s/%s/%s/%s",
                dataUser.getDataUserDir(), respeakApp.authDataUser.get(0),
                dataUser.getMessDir(), dataUser.getMessFileName()
        );
        for (String s : fileIO.fileReadLastLines(pathFileMessage, nLastMessage)) {
            messageBox.appendText(s);
        }
        messageBox.appendText("\n\n––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––\n");
    }

    @FXML
    private void closeWindows() {
        respeakApp.getChatStage().close();
    }

/*
    public void close() {
        ReadMessageListener readMessageListener;
        network.removeReadMessageListner(readMessageListener);
    }
*/


    private static class SingletonHelper {
        private static final RespeakController INSTANCE = new RespeakController();
    }

    public static RespeakController getInstance() {
        return SingletonHelper.INSTANCE;
    }
}