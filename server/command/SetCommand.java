package server.command;

import server.Receiver;
import server.Request;

public class SetCommand implements Command{
    Receiver receiver;
    public SetCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(Request request) {
        return receiver.set(request.getKey(), request.getValue());
    }
}
