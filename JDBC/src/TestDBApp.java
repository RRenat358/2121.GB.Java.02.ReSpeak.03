

public class TestDBApp {

    static Jdbc jdbc = new Jdbc();

    public static void main(String[] args) {
        try {
            jdbc.connect();

            jdbc.createTable();
//            jdbc.clearTable();

            jdbc.insertUserN();
//            jdbc.insertUser5Batch();
//            jdbc.insertUser5Transaction();

            jdbc.insertUser("Martin", "mmm");
//            jdbc.insertUserPrepared("Tom", "ttt");

            jdbc.readDB();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            jdbc.disconnect();
        }
    }



}
