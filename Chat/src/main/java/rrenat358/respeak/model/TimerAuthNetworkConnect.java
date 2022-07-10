package rrenat358.respeak.model;

import javafx.application.Platform;
import rrenat358.respeak.RespeakApp;
import rrenat358.respeak.controllers.AuthController;
import rrenat358.respeak.controllers.RespeakController;
import rrenat358.respeak.dialogs.DialogEnum;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TimerAuthNetworkConnect {

    private final Timer timer = new Timer();
    Network network = Network.getInstance();
    RespeakApp respeakApp;
    RespeakController respeakController;
    AuthController authController;

    private boolean timeOff = false;
    private boolean timeOffStarting = false;
    private int timeOffSeconds = 5000;


    public boolean authTimeOff() {
        if (timeOffStarting == false) {
            System.out.println("timer Start → → → → →");
            timeOffStarting = true;
            timeOffStart();
        }


        timeOff = true;

        System.out.println("timer Stop xxxxxxxxxxxxxx");

        return timeOff;
    }

    public void timeOffStart() {

        Thread threadTimer = new Thread(() -> {
            try {
                System.out.println("===Timer.sleep===  " + timeOffSeconds);
                Thread.sleep(timeOffSeconds);
                System.out.println("timer Stop xxxxxxxxxxxxxx");
                timeOff = true;
            } catch (InterruptedException e) {
                System.out.println("===Timer.sleep===");
                throw new RuntimeException(e);
            }
        });
        threadTimer.start();
    }


/*
    public void authTaskConnect() {
        System.out.println("timer Start → → → → →");
        Thread threadTimer = new Thread();
        threadTimer.setDaemon(true);

        try {
            threadTimer.start();
//            threadTimer.join();
            threadTimer.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("authTaskConnect() -- threadTimer.sleep");
            throw new RuntimeException(e);
        }
        threadTimer.interrupt();
        System.out.println("timer Stop xxxxxxxxxxxxxx");
//        DialogEnum.AuthError.LOGOPASS_INVALID.show();
//        respeakApp.getAuthStage().close();
//        respeakApp.getChatStage().close();
//        authController.close();
//        network.readMessageProcessinterrupt();
        network.socketClose();

    }
*/

/*
    public void authTaskConnect1() {
        System.out.println("timer Start → → → → →");

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!network.isConnected()) {
                        System.out.println("timer Stop xxxxxxxxxxxxxx");
                        DialogEnum.AuthError.LOGOPASS_INVALID.show();
//                        network.socketClose();
//                        network.readMessageProcessinterrupt();
//                        respeakApp.getAuthStage().close();
//                        respeakApp.getChatStage().close();
//                        respeakController.closeWindows();
//                        respeakApp.getAuthController().close();
//                        respeakApp.getAuthStage().close();

                        authController.close();

                    }
                });
            }
        }, 3, TimeUnit.SECONDS);
    }
*/










/*

    public void startConnectionAndStopTime(*/
    /*int timeStop*//*
RespeakApp respeakApp) {

        System.out.println("timer Start → → → → →");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer Stop xxxxxxxxxxxxxx");
//                respeakApp.getAuthController().closeNetwork();
//                network.socketClose();
//                network.readMessageProcessInterrupt();
//                closeNetwork();

            }
        }, 3000);
    }
*/



    private static class SingletonHelper {
        private static final TimerAuthNetworkConnect INSTANCE = new TimerAuthNetworkConnect();
    }

    public static TimerAuthNetworkConnect getInstance() {
        return TimerAuthNetworkConnect.SingletonHelper.INSTANCE;
    }
}