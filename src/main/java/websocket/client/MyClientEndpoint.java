package websocket.client;

import java.io.IOException;
import javax.websocket.*;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint()
public class MyClientEndpoint {
    final Logger logger = LoggerFactory.getLogger(MyClientEndpoint.class);
    private static Session server = null;
    private static int counter = 0;

    @OnOpen
    public void onOpen(Session session) {
        server = session;
        logger.info("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void message(String message) throws IOException, EncodeException {
        counter++;
        System.out.println(counter);
        logger.info(message);
        if (counter == 15){
            try {
                server.getBasicRemote().sendObject(buildCommand(2,2,3, 30).toJSONString());
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
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