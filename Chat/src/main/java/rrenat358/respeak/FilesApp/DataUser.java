package rrenat358.respeak.FilesApp;


import java.io.File;
import java.io.IOException;


public class DataUser {

    private static String pathToDataDir = "";
    private static String dataUserDir = "DataUser";

    private static String messDir = "Messages";
    private static String messFile = messDir + ".txt";


    public static void MkdirUser(String userLogin) {
        File userDir = new File( String.format("%s/%s", pathToDataDir, dataUserDir));

        if (!userDir.exists()) {
            userDir.mkdirs();
        }
    }


    public static void createFileMessageHistory(String userLogin) {
        File messHistory = new File(pathToDir + userLogin + messDir,
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
