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
    private String[] userName3;
    private String[] userName4;
    private String[] userNameDB5;
    private User userName5;

    public String getUserNameByLoginPassword2(String login, String password) {
        userName = dbConnect.isLoginPass2(login, password);
        if (userName != null) {
            return userName;
        }
        return null;
    }

    public String getUserNameByLoginPassword3(String login, String password) {
        User userRequired = new User(login, password);
        userName3 = this.dbConnect.isLoginPass3(login, password);
//        for (User user : User userName2) {
//        User[] user = userName2;
            if (userRequired.equals(userName3)) {
                return userName3[2];
            }
//        }
        return null;
    }


    public String getUserNameByLoginPassword4(String login, String password) {
        User userRequired = new User(login, password);

        userName4 = dbConnect.isLoginPass3(login, password);
        User dbUser = new User(userName4[0],userName4[1]);

        if (userRequired.equals(dbUser)) {
            return userName4[2];
        }
        return null;
    }


    public String getUserNameByLoginPassword5(String login, String password) {
        User userRequired = new User(login, password);

        userNameDB5 = dbConnect.isLoginPass3(login, password);
        userName5 = new User(userNameDB5[0],userNameDB5[1],userNameDB5[3]);

        if (userRequired.equals(userName5)) {
            return userName5.getUserName();
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
