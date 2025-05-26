package server.command;

import server.Receiver;
import server.Request;

public class GetCommand implements Command{
    Receiver database;
    public GetCommand(Receiver database) {
        this.database = database;
    }
    @Override
    public String execute(Request request) {
        return database.get(request.getKey());
    }
}
