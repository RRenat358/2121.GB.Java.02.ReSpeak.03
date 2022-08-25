package rrenat358.respeak.FileHandler;


import java.io.File;
import java.io.IOException;


public class DataUser {

    //todo задавать пути и имена из -- ??
    private String pathToDataDir = "";
    private String dataUserDir = "DataUser";

    private String messDir = "Messages";
    private String messFileName = messDir + ".txt";

    private String logDir = "Logs";
    private String logFileName = logDir + ".txt";


    public  void createDataUser(String nameUser) {
        if (pathToDataDir == "") {
            String pathToNameUserDir = String.format("%s/%s/%s", pathToDataDir, dataUserDir, nameUser);
        }
        String pathToNameUserDir = String.format("%s/%s", dataUserDir, nameUser);


        createDirs(pathToNameUserDir);
        createFiles(pathToNameUserDir);
    }

    public void createDirs(String pathToNameUserDir) {
        File userDir = new File( pathToNameUserDir);
        if (!userDir.exists()) {
            userDir.mkdirs();
        }
    }

    public void createFiles(String pathToNameUserDir) {

        File messFile = new File(String.format("%s/%s/", pathToNameUserDir, messDir, messFileName));
        if (!messFile.exists()) {
            try {
                messFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("Файл %s не создан", messFileName);
            }
        }

        File logFile = new File(String.format("%s/%s/", pathToNameUserDir, logDir, logFileName));
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("Файл %s не создан", logFileName);
            }
        }



    }



}
