import java.sql.*;

public class Jdbc {

    private static Connection connection;
    private static Statement statement;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:ChatDB.sqlite");
        statement = connection.createStatement();
    }

    public void disconnect() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void createTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT exists User\n" +
                "(\n" +
                "    id integer primary key autoincrement not null,\n" +
                "    name text not null ,\n" +
                "    password text not null ,\n" +
                "    age integer\n" +
                ")");
    }

    public void clearTable() throws SQLException {
        statement.executeUpdate("DELETE FROM main.User");
        statement.executeUpdate("DELETE FROM sqlite_sequence WHERE name='User'");
    }

    public void dropTable() throws SQLException {
        statement.executeUpdate("DROP TABLE main.User");
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


    public void insertUserN() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO User (name, password)\n" +
                        "VALUES (?, ?)")) {
            for (int i = 1; i <= 5; i++) {
                ps.setString(1, "User_" + i);
                ps.setString(2, i + "");
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void readDB() {
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
