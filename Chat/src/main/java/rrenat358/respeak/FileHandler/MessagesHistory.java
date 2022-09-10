package rrenat358.respeak.FileHandler;


import rrenat358.respeak.controllers.RespeakController;

public class MessagesHistory {

    String pathFileMessage = "";
    int partElement = 10;

    private RespeakController respeakController = RespeakController.getInstance();
    private FileIO fileIO = FileIO.getInstance();


    public void messageHistoryPartElement(String pathFile, int partElement) {
        for (String s : fileIO.fileReadLastLines(pathFile, partElement))
            respeakController.messageBox.appendText(s);
    }




}
