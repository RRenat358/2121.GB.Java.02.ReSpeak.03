package rrenat358.respeak.model;


public class TimerAuthNetworkConnect {

    private boolean timeOff = false;
    private Thread threadTimer;

    public boolean timeOff() {
        return timeOff;
    }

    public void timeOffStart(int timeOffMilliSeconds) {
        System.out.println("authTimeOffStart() START → → → " + timeOffMilliSeconds + "ms.");

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
        return SingletonHelper.INSTANCE;
    }
}