package websocket.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.websocket.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint(
        encoders = { MessageEncoder.class },
        decoders = { MessageDecoder.class }
)
public class MyClientEndpoint {
    final Logger logger = LoggerFactory.getLogger(MyClientEndpoint.class);

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected to endpoint: " + session.getBasicRemote());
        try {
            Message response = new Message();
            response.setParam1("client0 message");
            response.setParam2("Param2");
            logger.info("TO server: " + response.getParam1());
            session.getBasicRemote().sendObject(response);
        } catch (IOException | EncodeException ex) {
            logger.info(MyClientEndpoint.class.getName(), ex);
        }
    }

    @OnMessage
    public void message(Message message, Session session) throws IOException, EncodeException {
        try {
            for(int i=1; i<10; i++) {
                logger.info("FROM server: " + message.getParam1());
                Message response = new Message();
                response.setParam1("client" + i + " message");
                response.setParam2("Param2");
                session.getBasicRemote().sendObject(response);
                logger.info("TO server: " + response.getParam1());
                TimeUnit.SECONDS.sleep(3);
            }
            Client.messageLatch.countDown();
        } catch (EncodeException | InterruptedException | IOException ex) {
            logger.info(MyClientEndpoint.class.getName(), ex);
        }
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }
}
