import java.sql.*;

public class Jdbc {

    private static Connection connection;
    private static Statement statement;

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:ChatDB.sqlite");
        statement = connection.createStatement();


    }


    public static void createTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT exists User\n" +
                "(\n" +
                "    id integer primary key autoincrement not null,\n" +
                "    name text not null ,\n" +
                "    password text not null ,\n" +
                "    age integer\n" +
                ")");
    }

    public void insertUser(String name, String password) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO User (name, password)\n" +
                        "VALUES (?, ?)")) {
            ps.setString(1,name);
            ps.setString(2, password);
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void insertUserN() throws SQLException {
        for (int i = 1; i <= 5; i++) {
            statement.executeUpdate("INSERT INTO User (name, password)\n" +
                    "VALUES ('User_" + i + "', '" + i + "')");
        }
    }

    public static void readDB() {
        try (ResultSet rs = statement.executeQuery("SELECT * FROM User")) {
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " " +
                                rs.getString(2) + " " +
                                rs.getString(3) + " " +
                                rs.getInt(4)
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
