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


    private RespeakApp respeakApp = RespeakApp.getInstance();
    private Network network = Network.getInstance();

    public void sendMessage() {
        String message = messageTextField.getText().trim();
        if (message.isEmpty()) {
            messageInputRequestFocus();
            return;
        }

        String recipient = null;
        if (!userListing.getSelectionModel().isEmpty()) {
            recipient = userListing.getSelectionModel().getSelectedItem().toString();
        }

        try {

            String[] alternativePrivateMessage = isAlternativePrivateMessage(message);
            if (alternativePrivateMessage != null){
                network.sendPrivateMessage(alternativePrivateMessage[0], alternativePrivateMessage[1]);
            } else

/*
            //==================================================
            // miniMessage == "/w u m" == 6 simbol
            if (message.length() >= 6) {
//                String[] messageSlit = message.split(" ", 3);
                String[] messageSlit = message.split("\\s+", 3);
                if (messageSlit.length == 3 && messageSlit[0].equals("/*")) {

                    System.out.println("Command send: /*");

                    for (Object messageSlit2 : userListing.getItems()) {
                        System.out.println(messageSlit2);
                    }


*/
/*
                    String us = null;
                    userListing.getItems().stream().forEach(new Consumer() {

                        @Override
                        public void accept(Object c) {
                            System.out.println(c);
                            if (c == messageSlit[1]) {
                                us = String.valueOf(c);
                            }
                        }
                    });
                    network.sendPrivateMessage(us, message);
*/

//                    System.out.println(userListing.getItems().listIterator());
/*
                    while(userListing.getItems().listIterator().hasNext()) {
                        System.out.println(userListing.getItems().listIterator().next());
                    }
*/


//                    if (messageSlit[1] == "я") {
/*                    if (messageSlit[1].equals("я")) {
                        System.out.println("Command send: /w яяя");


                    }*/


//                            processMessage("data1 == /w");
//                    break;

/*
                }
            }
*/


                //==================================================
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
                } else if (command.getType() == CommandType.UPDATE_USERS_LIST) {
                    UpdateUserListCommandData data = (UpdateUserListCommandData) command.getData();
                    Platform.runLater(() -> {
                        userListing.setItems(FXCollections.observableArrayList(data.getUsers()));
                    });
                }
            }
        });
    }


    //==================================================
    private String[] isAlternativePrivateMessage(String message) {
        String[] recipientAndMessage = {"", ""};
        if (message.length() >= 6) {
            String[] messageSplit = message.split("\\s+", 3);
            if (messageSplit.length == 3 && messageSplit[0].equals("/*")) {

                for (Object targetRecipient : userListing.getItems()) {
                    System.out.println("-------------------------searchRecipientAlternativePrivateMessage");
                    if (messageSplit[1].equals(targetRecipient)) {
                        recipientAndMessage[0] = String.valueOf(messageSplit[1]);
                        recipientAndMessage[1] = String.valueOf(messageSplit[2]);
                        return recipientAndMessage;
                    }
                }
            }
        }
/*
        String[] recipient = alternativePrivateMessage(message);

        if (recipient != null) {
            searchRecipientAlternativePrivateMessage(recipient[1]);
            return searchRecipientAlternativePrivateMessage(recipient[1]);
        }
*/
        return null;
    }

/*
    private String[] alternativePrivateMessage(String message) {
        if (message.length() >= 6) {
            String[] messageSplit = message.split("\\s+", 3);
            if (messageSplit.length == 3 && messageSplit[0].equals("/*")) {
                return String.valueOf(messageSplit[1]);
            }
        }
        return null;
    }

    private String searchRecipientAlternativePrivateMessage(String recipient) {
        for (Object targetRecipient : userListing.getItems()) {
            System.out.println("searchRecipientAlternativePrivateMessage");
            if (recipient.equals(targetRecipient)) {
                return String.valueOf(targetRecipient);
            }
        }
        return null;
    }
*/

    //==================================================
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