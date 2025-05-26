package server;

import server.command.*;

import java.util.Map;

public class Invoker {
    Receiver database = new Receiver();
    private final Map<String, Command> handlers = Map.of(
            "get", new GetCommand(database),
            "set", new SetCommand(database),
            "delete", new DeleteCommand(database),
            "exit", new ExitCommand(database)
    );
    public String process(Request request) {
        Command cmd = handlers.get(request.getType());
        return cmd.execute(request);
    }
}
