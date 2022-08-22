package ru.rrenat358.server.auth;

import rrenat358.respeak.model.Network;
import ru.rrenat358.dbconnect.DBConnect;

import java.util.ArrayList;
import java.util.Set;



public class AuthService {

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
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

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    private DBConnect dbConnect;





    private String userName;
    public String getUserNameByLoginPassword2(String login, String password) {
        userName = dbConnect.isLoginPass2(login, password);
        if (userName != null) {
            return userName;
        }
        return null;
    }


    private String[] userName3;
    public String getUserNameByLoginPassword3(String login, String password) {
        User userRequired = new User(login, password);
        userName3 = this.dbConnect.isLoginPass3(login, password);
            if (userRequired.equals(userName3)) {
                return userName3[2];
            }
        return null;
    }

    private String[] userName4;
    public String getUserNameByLoginPassword4(String login, String password) {
        User userRequired = new User(login, password);

        userName4 = dbConnect.isLoginPass3(login, password);
        User dbUser = new User(userName4[0],userName4[1]);

        if (userRequired.equals(dbUser)) {
            return userName4[2];
        }
        return null;
    }


    private String[] userNameDB5;
    private User userName5;
    public String getUserNameByLoginPassword5(String login, String password) {
        User userRequired = new User(login, password);

        userNameDB5 = dbConnect.isLoginPass3(login, password);
        userName5 = new User(userNameDB5[0],userNameDB5[1],userNameDB5[3]);

        if (userRequired.equals(userName5)) {
            return userName5.getUserName();
        }
        return null;
    }


    //Точно такой же метод, только данные о юзере не из Листа, а из БД
    private String[] userNameDB6;
    private User userName6;
    public String getUserNameByLoginPassword6(String login, String password) {
        User userRequired = new User(login, password);

        userNameDB6 = dbConnect.isLoginPass3(login, password);
        userName6 = new User(userNameDB6[0],userNameDB6[1],userNameDB6[3]);
/*
        for (User user : userName5) {
            if (userRequired.equals(user)) {
                return user.getUserName();
            }
        }
*/
        User user = null;
        if (user == userName6) {
            if (userRequired.equals(user)) {
                return user.getUserName();
            }
        }
        return null;
    }



    private String[] userNameDB7;
    private User userName7;
    public synchronized String getUserNameByLoginPassword7(String login, String password) {
        Thread thread2 = new Thread(() -> {
            userNameDB7 = dbConnect.isLoginPass3(login, password);
        });
        try {
            thread2.start();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        User userRequired = new User(login, password);
        userName7 = new User(userNameDB7[0],userNameDB7[1],userNameDB7[3]);

        if (userRequired.equals(userName7)) {
            return userName7.getUserName();
        }
        return null;
    }


    private String[] userNameDB8;
    private User userName8;
    public synchronized String getUserNameByLoginPassword8(String login, String password) {
        Network network = new Network();
        network.startReadMessageProcess().setDaemon(false);

        Thread thread2 = new Thread(() -> {
            userNameDB8 = dbConnect.isLoginPass3(login, password);
            System.out.println(userNameDB8[0]+userNameDB8[1]+userNameDB8[3]);
        });
        try {

            thread2.start();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        User userRequired = new User(login, password);
        System.out.println(userRequired);
        userName8 = new User(userNameDB8[0],userNameDB8[1],userNameDB8[3]);

        if (userRequired.equals(userName8)) {
            return userName8.getUserName();
        }
        return null;
    }


    //Точно такой же метод, только данные о юзере не из Листа, а из БД
    private ArrayList<String> userDBLogPassName = new ArrayList<>();

    public String getUserNameByLoginPassword9(String login, String password) {
        User userRequired = new User(login, password);
        userDBLogPassName = dbConnect.isLoginPass4(login, password);

        Set<User> USERS9 = Set.of(
                new User(userDBLogPassName.get(0), userDBLogPassName.get(1), userDBLogPassName.get(2)));

        for (User user : USERS9) {
            if (userRequired.equals(user)) {
                return user.getUserName();
            }
        }
        return null;
    }





    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    private static class SingletonHelper {
        private static final AuthService INSTANCE = new AuthService();
    }

    public static AuthService getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
