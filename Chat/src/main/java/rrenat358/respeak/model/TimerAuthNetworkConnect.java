package rrenat358.respeak.model;


import rrenat358.respeak.RespeakApp;

public class TimerAuthNetworkConnect {

    private boolean timeOff = false;
    private boolean timeOffStarting = false;

    private Thread threadTimer;
    private RespeakApp respeakApp = RespeakApp.getInstance();

    public boolean authTimeOff() {
        if (timeOff == true) {
            return true;
        }
        if (timeOffStarting == false) {
            authTimeOffStart(4);
            return false;
        }
        return timeOff;
    }


    public void authTimeOffStart(int timeOffMilliSeconds) {
        System.out.println("authTimeOffStart() START → → → " + timeOffMilliSeconds + "ms.");
        timeOffStarting = true;

        threadTimer = new Thread(() -> {
            try {
                Thread.sleep(timeOffMilliSeconds);
                System.out.println("=== TIMER STOP ✖ ===");
                timeOff = true;
            } catch (InterruptedException e) {
                System.err.println("=== timeOffStart() ===");
                System.err.println("Thread.sleep() --> interrupt()");
                System.err.println("All right");
            }
        });
        threadTimer.start();
    }


    public Thread getThreadTimer() {
        return threadTimer;
    }

    private static class SingletonHelper {
        private static final TimerAuthNetworkConnect INSTANCE = new TimerAuthNetworkConnect();
    }

    public static TimerAuthNetworkConnect getInstance() {
        return TimerAuthNetworkConnect.SingletonHelper.INSTANCE;
    }
}