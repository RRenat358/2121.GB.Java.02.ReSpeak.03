package rrenat358.respeak.FilesApp;


import java.io.File;
import java.io.IOException;


public class MkdirUser {

    private static String pathToDir = "Chat/src/main/Files/Users/";
    private static String messDir = "Messages";
//        String userDir = userLogin;


    public static void MkdirUser(String userLogin) {
        File userDir = new File(pathToDir + userLogin);

        if (!userDir.exists()) {
            userDir.mkdirs();
        }
    }


    public static void createFileMessageHistory(String userLogin) {
        File messHystory = new File(pathToDir + userLogin + messDir, messDir + ".txt");



        if (!messHystory.exists()) {
            try {
                messHystory.createNewFile();
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
