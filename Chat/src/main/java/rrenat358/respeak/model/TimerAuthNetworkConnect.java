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

    private RespeakApp respeakApp = RespeakApp.getInstance();
    private int timeOffMilliSeconds = respeakApp.authTimeOffSeconds * 1000;

    private boolean timeOff = false;
    private boolean timeOffStarting = false;
    public static Thread threadTimer;

    public boolean authTimeOff() {
        if (timeOff == true) {
            return true;
        }
        if (timeOffStarting == false) {
            System.out.println("authTimeOff Start → → → " + respeakApp.authTimeOffSeconds + " sec");
            timeOffStarting = true;
            timeOffStart();
            return false;
        }
        return timeOff;
    }


    public void timeOffStart() {
        threadTimer = new Thread(() -> {
            try {
                Thread.sleep(timeOffMilliSeconds);
                System.out.println("timer Stop xxxxxxxxxxxxxx");
                timeOff = true;
            } catch (InterruptedException e) {
                System.err.println("===timeOffStart()===");
                throw new RuntimeException(e);
            }
        });
        threadTimer.start();
    }


    private static class SingletonHelper {
        private static final TimerAuthNetworkConnect INSTANCE = new TimerAuthNetworkConnect();
    }

    public static TimerAuthNetworkConnect getInstance() {
        return TimerAuthNetworkConnect.SingletonHelper.INSTANCE;
    }
}