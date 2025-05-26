package server.command;

import server.Receiver;
import server.Request;

public class DeleteCommand implements Command{
    Receiver database;
    public DeleteCommand(Receiver database) {
        this.database = database;
    }

    @Override
    public String execute(Request request) {
        return database.delete(request.getKey());
    }
}
