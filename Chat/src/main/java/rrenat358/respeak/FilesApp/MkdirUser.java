package rrenat358.respeak.FilesApp;


import java.io.File;


public class MkdirUser {

    public static void main(String[] args) {
        MkdirUser("555");
    }


    public static void MkdirUser(String userLogin) {

//        String userDir = userLogin + ".txt";
        String pathToDir = "Chat/src/main/Files/Users/";

        File userDir = new File(pathToDir + userLogin);

        if (!userDir.exists()) {
            userDir.mkdirs();
        }




    }


}
