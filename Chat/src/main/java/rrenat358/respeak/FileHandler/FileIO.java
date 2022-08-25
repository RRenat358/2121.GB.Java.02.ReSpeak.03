package rrenat358.respeak.FileHandler;


import java.io.*;

public class FileIO {

    public void writeNewLineToFile(String file, String string) {
        try (Writer writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append("\n").append(string);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.printf("Запись в файл %s не создана", file);
        }
    }

    public void writeContinueLineToFile(String file, String separator, String string) {
        try (Writer writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(separator).append(string);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.printf("Запись в файл %s не создана", file);
        }
    }




    private static class SingletonHelper {
        private static final FileIO INSTANCE = new FileIO();
    }

    public static FileIO getInstance() {
        return SingletonHelper.INSTANCE;
    }


}
