package rrenat358.respeak.FileHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * for Tasting
 */


public class FileHandlerTestApp {

    private static FileIO fileIO = FileIO.getInstance();

    public static void main(String[] args) {

        String string = "65418468543";
        String file = "DataUser/FileHandlerTestApp.txt";
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                "yyyy.MM.dd. HHmmss"));


        DataUser dataUser = new DataUser();
//        FileIO fileIO = new FileIO();





//        dataUser.createDataUser("1447");



        fileIO.writeNewLineToFile(file, localDateTime);
        fileIO.writeContinueLineToFile(file, " ", localDateTime);




    }



}
