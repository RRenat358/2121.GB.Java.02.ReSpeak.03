package rrenat358.respeak.FileHandler;


import rrenat358.respeak.controllers.RespeakController;

//todo using by future
public class MessageHistory {

    String pathFileMessage = "";
    int partElement = 10; //default

    private RespeakController respeakController = RespeakController.getInstance();
    private FileIO fileIO = FileIO.getInstance();


    //todo using by future
    public void messageHistoryPartElement(String pathFile, int partElement) {
        for (String s : fileIO.fileReadLastLines(pathFile, partElement))
            respeakController.messageBox.appendText(s);
    }




}
