package rrenat358.respeak.FileHandler;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.apache.commons.io.input.ReversedLinesFileReader;
import java.util.Collections;


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



    //======================================================================
    public static ArrayList<String> fileReadLastLines(String pathFile, int nLast) {
        ArrayList<String> arrayList = new ArrayList<>();
        try (ReversedLinesFileReader readLastLine = new ReversedLinesFileReader(
                new File(pathFile), StandardCharsets.UTF_8)) {

            String lastLine;
            int countLine = 0;

            while (countLine < nLast && (lastLine = readLastLine.readLine()) != null) {
                arrayList.add(lastLine);
                countLine++;
            }
            Collections.reverse(arrayList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }





    private static class SingletonHelper {
        private static final FileIO INSTANCE = new FileIO();
    }

    public static FileIO getInstance() {
        return SingletonHelper.INSTANCE;
    }


}
