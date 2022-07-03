package rrenat358.respeak.model;

import rrenat358.respeak.RespeakApp;

import java.util.Timer;
import java.util.TimerTask;

public class TimerAuthNetworkConnect {

    private final Timer timer = new Timer();
    Network network = Network.getInstance();

    public void startConnectionAndStopTime(/*int timeStop*/RespeakApp respeakApp) {

        System.out.println("timer Start → → → → →");
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                System.out.println("timer Stop xxxxxxxxxxxxxx");
//                respeakApp.getAuthController().closeNetwork();
//                network.socketClose();
                network.readMessageProcessInterrupt();
//                closeNetwork();

            }
        }, 3000);
    }




}
