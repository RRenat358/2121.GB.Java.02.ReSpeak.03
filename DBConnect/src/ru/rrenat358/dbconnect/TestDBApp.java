package ru.rrenat358.dbconnect;

public class TestDBApp {

    static DBConnect DBConnect = new DBConnect();

    public static void main(String[] args) {
        try {
            DBConnect.connect();

//            DBConnect.clearTable();      // !!!
//            DBConnect.dropTable();     // !!!

//            DBConnect.createTable();
//            DBConnect.clearTable();

            /*
            DBConnect.insertUser("Martin", "mmm");
            DBConnect.insertUser("Tom", "ttt");
            DBConnect.insertUser("Den", "ddd", "Denis");

            DBConnect.insertUserN();     // fori = 5
            */

//            DBConnect.readDB();

            DBConnect.isLoginPass("aaa","bbb");
            DBConnect.isLoginPass("zzz","xxx");
            DBConnect.isLoginPass("Den","ddd");


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBConnect.disconnect();
        }
    }



}
