package rrenat358.respeak.model;

import javafx.application.Platform;
import rrenat358.respeak.RespeakApp;
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

    public void startConnectionAndStopTime(/*int timeStop*/RespeakApp respeakApp) {

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

    public void authTaskConnect() {
        System.out.println("timer Start → → → → →");

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!Network.getInstance().isConnected()) {
                        System.out.println("timer Stop xxxxxxxxxxxxxx");
                        DialogEnum.AuthError.LOGOPASS_INVALID.show();
//                        network.socketClose();
//                        network.readMessageProcessinterrupt();
//                        respeakApp.getAuthStage().close();
//                        respeakApp.getChatStage().close();
//                        respeakController.closeWindows();
                        respeakApp.getAuthController().close();
                        respeakApp.getAuthStage().close();

                    }
                });
            }
        }, 3, TimeUnit.SECONDS);
    }

}