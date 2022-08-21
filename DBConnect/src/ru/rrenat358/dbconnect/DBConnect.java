package ru.rrenat358.dbconnect;

import java.sql.*;
import java.util.Objects;

public class DBConnect {

    private static Connection connection;
    private static Statement statement;

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
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

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public void createTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT exists User \n" +
                "(\n" +
                "    id integer primary key autoincrement not null ,\n" +
                "    login text not null ,\n" +
                "    password text not null ,\n" +
                "    name text ,\n" +
                "    age integer\n" +
                ")");
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public void clearTable() throws SQLException {
        statement.executeUpdate("DELETE FROM main.User");
        statement.executeUpdate("DELETE FROM sqlite_sequence WHERE name='User'");
    }

    public void dropTable() throws SQLException {
        statement.executeUpdate("DROP TABLE main.User");
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public void insertUser(String login, String password) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO main.User (login, password)\n" +
                        "VALUES (?, ?)")) {
            ps.setString(1, login);
            ps.setString(2, password);
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertUser(String login, String password, String name) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO main.User (login, password, name)\n" +
                        "VALUES (?, ?, ?)")) {
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void insertUserN() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO User (login, password, name)\n" +
                        "VALUES (?, ?, ?)")) {
            for (int i = 1; i <= 5; i++) {
                ps.setString(1, "u" + i);
                ps.setString(2, "p" + i);
                ps.setString(3, "User0" + i);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public void readDB() {
        try (ResultSet rs = statement.executeQuery("SELECT * FROM User")) {
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " " +
                                rs.getString(2) + " " +
                                rs.getString(3) + " " +
                                rs.getString(4) + " " +
                                rs.getInt(5)
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    String login;
    String password;

    public boolean isLoginPass(String isLogin, String isPassword) {
        this.login = null;
        this.password = null;

        try (ResultSet rs = statement.executeQuery(
                "select login, password \n" +
                        "from User \n" +
                        "where login = '" + isLogin + "' " +
                        "and password = '" + isPassword + "'"))
        {
            while (rs.next()) {
                this.login = rs.getString(1);
                this.password = rs.getString(2);
            }

            if (this.login == null) {
                return false;
            }
            System.out.println(this.login + " " + this.password);

            if (Objects.equals(this.login, isLogin) && Objects.equals(this.password, isPassword)) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


}
