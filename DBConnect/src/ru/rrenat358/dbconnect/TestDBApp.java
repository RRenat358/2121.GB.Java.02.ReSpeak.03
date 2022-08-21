package ru.rrenat358.dbconnect;

public class TestDBApp {

    static DBConnect DBConnect = new DBConnect();

    public static void main(String[] args) {
        try {
            DBConnect.connect();

//            DBConnect.createTable();
//            DBConnect.clearTable();      // !!!
//            DBConnect.dropTable();     // !!!

            DBConnect.insertUser("Martin", "mmm");
            DBConnect.insertUser("Tom", "ttt");

            DBConnect.insertUserN();     // fori = 5

            DBConnect.readDB();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBConnect.disconnect();
        }
    }



}
