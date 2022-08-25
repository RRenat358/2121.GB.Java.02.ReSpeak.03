package rrenat358.respeak.FilesApp;


import java.io.File;
import java.io.IOException;


public class DataUser {

    private static String pathToDataDir = "";
    private static String dataUserDir = "DataUser";

    private static String pathToDataUserDir = String.format("%s/%s", pathToDataDir, dataUserDir);

    private static String messDir = "Messages";
    private static String messFile = messDir + ".txt";

    private static String logDir = "Logs";
    private static String logFile = logDir + ".txt";


    public static void createDataUser(String nameUser) {

        File userDir = new File( pathToDataUserDir);

        if (!userDir.exists()) {
            userDir.mkdirs();
        }





        
    }


    public static void createFileMessageHistory(String nameUser) {
        File messHistory = new File(pathToDir + nameUser + messDir,
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
        createDataUser("555");
    }

}
