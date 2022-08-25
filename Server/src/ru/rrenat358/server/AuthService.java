package ru.rrenat358.server;

import ru.rrenat358.dbconnect.DBConnect;

import java.util.ArrayList;


public class AuthService {

    private DBConnect dbConnect = new DBConnect();
    private ArrayList<String> userDBLogPassName = new ArrayList<>();

    public String getUserNameByLogPass(String login, String password) {
        userDBLogPassName.clear();
        userDBLogPassName = dbConnect.isLogPass(login, password);
        if (userDBLogPassName.isEmpty()) {
            return null;
        }
        if (userDBLogPassName.get(0) == null || userDBLogPassName.get(1) == null) {
            return null;
        }
        if (userDBLogPassName.get(2) == null) {
            return userDBLogPassName.get(0);
        }
        return userDBLogPassName.get(2);
    }


    private static class SingletonHelper {
        private static final AuthService INSTANCE = new AuthService();
    }

    public static AuthService getInstance() {
        return SingletonHelper.INSTANCE;
    }


}
