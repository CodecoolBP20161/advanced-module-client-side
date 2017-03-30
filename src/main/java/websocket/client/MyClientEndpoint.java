package websocket.client;

import javax.websocket.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint()
public class MyClientEndpoint extends Endpoint {
    final Logger logger = LoggerFactory.getLogger(MyClientEndpoint.class);
    private static Session server = null;

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        server = session;
        RemoteEndpoint.Basic remote = session.getBasicRemote();
        logger.info("Connected to endpoint: " + remote);
        session.addMessageHandler(new MyMessageHandler(remote));
    }

    public void onError(Session session, Throwable thr) {
        thr.printStackTrace();
    }

    public static JSONObject buildCommand(int player, int from, int to, int size){
        JSONObject json = new JSONObject();
        json.put("player", player);
        json.put("from", from);
        json.put("to", to);
        json.put("size", size);
        return json;
    }
}