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
                "yyyy.MM.dd. HHmmss"));

        try (Writer writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("\n=== Start DataUser ==============================\n");
            writer.write(localDateTime + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getPathToDataDir() {
        return pathToDataDir;
    }

    public void setPathToDataDir(String pathToDataDir) {
        this.pathToDataDir = pathToDataDir;
    }

    public String getDataUserDir() {
        return dataUserDir;
    }

    public void setDataUserDir(String dataUserDir) {
        this.dataUserDir = dataUserDir;
    }

    public String getMessDir() {
        return messDir;
    }

    public void setMessDir(String messDir) {
        this.messDir = messDir;
    }

    public String getMessFileName() {
        return messFileName;
    }

    public void setMessFileName(String messFileName) {
        this.messFileName = messFileName;
    }

    public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }




    private static class SingletonHelper {
        private static final DataUser INSTANCE = new DataUser();
    }

    public static DataUser getInstance() {
        return SingletonHelper.INSTANCE;
    }

}
