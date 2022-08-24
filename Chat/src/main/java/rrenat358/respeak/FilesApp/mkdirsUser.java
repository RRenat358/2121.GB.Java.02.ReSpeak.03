package rrenat358.respeak.FilesApp;


import java.io.File;

public class mkdirsUser {




    public void mkdirsMessage(String userLogin) {

        String demo1 = "dir01/demo1.txt";

        File dir01 = new File(demo1);
        if (!dir01.exists()) {
            dir01.mkdirs();
        }


    }



}
