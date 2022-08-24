package rrenat358.respeak.FilesApp;


import java.io.File;
import java.io.IOException;


public class MkdirUser {

    private static String pathToDir = "Chat/src/main/Files/Users/";
    private static String messDir = "/Messages/";
    private static String messFile = "Messages.txt";
//        String userDir = userLogin;


    public static void MkdirUser(String userLogin) {
        File userDir = new File(pathToDir + userLogin + messDir);

        if (!userDir.exists()) {
            userDir.mkdirs();
        }
    }


    public static void createFileMessageHistory(String userLogin) {
        File messHistory= new File(pathToDir + userLogin + messDir,
                messFile);

        if (!messHistory.exists()) {
            try {
                messHistory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Файл MessageHistory не создан");
            }
        }




    }





    //for Tasting
    public static void main(String[] args) {
        MkdirUser("555");
    }

}
