package ru.rrenat358.server.auth;

import rrenat358.respeak.model.Network;
import ru.rrenat358.dbconnect.DBConnect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;



public class AuthService {

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    private static Set<User> USERS = Set.of(
            new User("1", "1", "userName1"),
            new User("2", "2", "userName2"),
            new User("3", "3", "userName3"),
            new User("4", "4")
    );

    public String getUserNameByLogPass1(String login, String password) {
        User userRequired = new User(login, password);
        for (User user : USERS) {
            if (userRequired.equals(user)) {
                return user.getUserName();
            }
        }
        return null;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    private DBConnect dbConnect =  new DBConnect();


    //todo переписать на сравнение в методе с запросом на стороне DB, т.к. там уже булево ок/неок
    private ArrayList<String> userDBLogPassName = new ArrayList<>();

    public String getUserNameByLogPass2(String login, String password) {
        userDBLogPassName.clear();
        User userRequired = new User(login, password);
        userDBLogPassName = dbConnect.isLogPass(login, password);
        Set<User> USERS2 = Set.of(
                new User(userDBLogPassName.get(0), userDBLogPassName.get(1), userDBLogPassName.get(2)));

        for (User user : USERS2) {
            if (userRequired.equals(user)) {
                return user.getUserName();
            }
        }
        return null;
    }

    public String getUserNameByLogPass3(String login, String password) {
        userDBLogPassName.clear();
        userDBLogPassName = dbConnect.isLogPass(login, password);
        if (userDBLogPassName.isEmpty()) {
            return null;
        }
        if (userDBLogPassName.get(0) == null || userDBLogPassName.get(1) == null) {
            return null;
        }
        if (userDBLogPassName.get(2) == null) {
            return "UserNoName";
        }
        return userDBLogPassName.get(2);
    }



    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    private static class SingletonHelper {
        private static final AuthService INSTANCE = new AuthService();
    }

    public static AuthService getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
