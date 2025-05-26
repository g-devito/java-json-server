package server.command;

import server.Receiver;
import server.Request;

public class ExitCommand implements Command {
    Receiver database;
    public ExitCommand(Receiver database) {
        this.database = database;
    }

    @Override
    public String execute(Request request) {
        return "exit";
    }
}
