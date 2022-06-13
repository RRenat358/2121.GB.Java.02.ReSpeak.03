package rrenat358.respeak.model;

import ru.rrenat358.command.Command;

public interface ReadMessageListener {

    void processReceivedCommand(Command command);
}
