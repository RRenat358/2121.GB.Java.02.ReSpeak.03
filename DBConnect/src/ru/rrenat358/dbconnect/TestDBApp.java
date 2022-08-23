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
//
//            DBConnect.insertUser("Martin", "mmm");
//            DBConnect.insertUser("Tom", "ttt");
//            DBConnect.insertUser("Den", "ddd", "Denis");
//
//            DBConnect.insertUser("1", "1", "User01");
//            DBConnect.insertUser("2", "2", "User02");
//            DBConnect.insertUser("3", "3", "User03");
//
//
//            DBConnect.insertUserN();     // fori = 5


//            DBConnect.readDB();

//            DBConnect.isLogPass("aaa","bbb");
//            DBConnect.isLogPass("zzz","xxx");
//            DBConnect.isLogPass("Den","ddd");

//            System.out.println(DBConnect.isLogPass("1", "1"));
//            System.out.println(DBConnect.isLogPass("3", "3"));

            System.out.println(DBConnect.isLogPass("1", "1"));
            System.out.println(DBConnect.isLogPass("3", "3"));


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBConnect.disconnect();
        }
    }



}
