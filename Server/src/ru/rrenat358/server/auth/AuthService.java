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
    private String userName;

    public synchronized String getUserNameByLoginPassword2(String login, String password) {
        userName = dbConnect.isLoginPass2(login, password);
        if (userName != null) {
            return userName;
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
