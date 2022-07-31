package hotciv.stub;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import spark.Request;

public class StubClientRequestHandler implements ClientRequestHandler {

    private final Invoker invoker;
    private String lastRequest;
    private String lastReply;

    public StubClientRequestHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String sendToServerAndAwaitReply(String request) {
        lastRequest = request;
        String reply = invoker.handleRequest(request);
        lastReply = reply;
        return reply;
    }




    @Override
    public void setServer(String hostname, int port) {
        // not used
    }

    @Override
    public void close() {

    }

    // Methods below are 'test retrieval' methods, used
    // to validate request/replies going through the CRH
    public String getLastRequest() {
        return lastRequest;
    }
    public String getLastReply() {
        return lastReply;
    }
}
