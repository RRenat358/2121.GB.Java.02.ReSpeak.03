package ru.rrenat358.server.auth;

import ru.rrenat358.dbconnect.DBConnect;

import java.util.Objects;
import java.util.Set;



public class AuthService {

    private static Set<User> USERS = Set.of(
            new User("1", "1", "userName1"),
            new User("2", "2", "userName2"),
            new User("3", "3", "userName3")
    );

    public String getUserNameByLoginPassword(String login, String password) {
        User userRequired = new User(login, password);
        for (User user : USERS) {
            if (userRequired.equals(user)) {
                return user.getUserName();
            }
        }
        return null;
    }

    private DBConnect dbConnect;

    public synchronized String getUserNameByLoginPassword2(String login, String password) {
        if (dbConnect.isLoginPass(login, password)) {
            Object o = "login";
            return (String) o;
        }
        return null;
    }











    private static class SingletonHelper {
        private static final AuthService INSTANCE = new AuthService();
    }

    public static AuthService getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
