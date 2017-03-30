package websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;

import static websocket.client.MyClientEndpoint.buildCommand;

public class MyMessageHandler implements MessageHandler.Whole<String> {
    final Logger logger = LoggerFactory.getLogger(MyClientEndpoint.class);
    private static int counter = 0;
    private RemoteEndpoint.Basic remoteEndpoint;

    public MyMessageHandler(RemoteEndpoint.Basic remote) {
        remoteEndpoint = remote;
    }

    @Override
    public void onMessage(String message) {
        counter++;
        System.out.println(counter);
        logger.info(message);
        if (counter == 15){
            try {
                remoteEndpoint.sendObject(buildCommand(2,2,3, 30).toJSONString());
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
