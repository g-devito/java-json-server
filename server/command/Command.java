package server.command;

import server.Request;

public interface Command {
    String execute(Request request);
}
