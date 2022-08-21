

public class TestDBApp {

    static Jdbc jdbc = new Jdbc();

    public static void main(String[] args) {
        try {
            jdbc.connect();

//            jdbc.createTable();
//            jdbc.clearTable();      // !!!
//            jdbc.dropTable();     // !!!

            jdbc.insertUser("Martin", "mmm");
            jdbc.insertUser("Tom", "ttt");

            jdbc.insertUserN();     // fori = 5

            jdbc.readDB();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbc.disconnect();
        }
    }



}
