package rrenat358.respeak.model;

import rrenat358.respeak.RespeakApp;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerAuthNetworkConnect {

    private final Timer timer = new Timer();
    Network network = Network.getInstance();
    RespeakApp respeakApp;

    public void startConnectionAndStopTime(/*int timeStop*/RespeakApp respeakApp) {

        System.out.println("timer Start → → → → →");
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                System.out.println("timer Stop xxxxxxxxxxxxxx");
//                respeakApp.getAuthController().closeNetwork();
//                network.socketClose();
//                network.readMessageProcessInterrupt();
//                closeNetwork();

            }
        }, 3000);
    }

    public void authTaskCorrect() {
        System.out.println("timer Start → → → → →");
        TimerTask timerTask = new TimerTask() {
            public void run() {
                System.out.println("timer Stop xxxxxxxxxxxxxx");
                respeakApp.getAuthController().close();
            }
        };
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(timerTask, 4000, 1, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
    }



}
