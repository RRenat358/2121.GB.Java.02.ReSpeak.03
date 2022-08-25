package rrenat358.respeak.FileHandler;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DataUser {

    //todo задавать пути и имена из -- ??
    private String pathToDataDir = "";
    private String dataUserDir = "DataUser";

    private String messDir = "Messages";
    private String messFileName = messDir + ".txt";

    private String logDir = "Logs";
    private String logFileName = logDir + ".txt";


    public void createDataUser(String nameUser) {
        if (pathToDataDir == "") {
            String pathToNameUserDir = String.format("%s/%s/%s", pathToDataDir, dataUserDir, nameUser);
        }
        String pathToNameUserDir = String.format("%s/%s", dataUserDir, nameUser);

        createDirs(pathToNameUserDir);
        createDirs(String.format("%s/%s", pathToNameUserDir, messDir));
        createDirs(String.format("%s/%s", pathToNameUserDir, logDir));

        createFiles(pathToNameUserDir);
    }


    public void createDirs(String pathToNameUserDir) {
        File userDir = new File(pathToNameUserDir);
        if (!userDir.exists()) {
            userDir.mkdirs();
        }
    }


    public void createFiles(String pathToNameUserDir) {

//        File messFile = new File(String.format("%s/%s/", pathToNameUserDir, messDir, messFileName));
        File messFile = new File(String.format("%s/%s/%s", pathToNameUserDir, messDir, messFileName));
        if (!messFile.exists()) {
            try {
                messFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("Файл %s не создан", messFileName);
            }
        }
        writeStartDataUser(messFile);


        File logFile = new File(String.format("%s/%s/%s", pathToNameUserDir, logDir, logFileName));
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("Файл %s не создан", logFileName);
            }
        }
        writeStartDataUser(logFile);

    }


    public void writeStartDataUser(File file) {
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                "yyyy.MM.dd-HHmmss"));

        try(Writer writer = new BufferedWriter(new FileWriter(file))) {
//            writer.write("=== Start DataUser ==============================\n");
//            writer.write(localDateTime + "\n");
            writer.append("=== Start DataUser ==============================\n");
            writer.append(localDateTime + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
