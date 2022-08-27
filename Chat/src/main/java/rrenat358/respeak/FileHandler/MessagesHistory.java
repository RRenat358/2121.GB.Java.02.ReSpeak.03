package rrenat358.respeak.FileHandler;


import rrenat358.respeak.controllers.RespeakController;

public class MessagesHistory {

    String pathFileMessage = "";
    int partElement = 10;

    private RespeakController respeakController = RespeakController.getInstance();
    private FileIO fileIO = FileIO.getInstance();




    public void messageHistoryPartElement(String pathFile, int partElement) {
/*
        String pathFileMessage = String.format(
                "%s/%s/%s/%s",
                dataUser.getDataUserDir(), respeakApp.authDataUser.get(0),
                dataUser.getMessDir(), dataUser.getMessFileName()
        );
*/

        for (String s : fileIO.fileReadLastLines(pathFile, partElement))
            respeakController.messageBox.appendText(s);
    }



}
