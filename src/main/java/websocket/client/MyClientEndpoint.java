package websocket.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.*;

@ClientEndpoint(
        encoders = { MessageEncoder.class },
        decoders = { MessageDecoder.class }
)
public class MyClientEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());

        try {
            Message response = new Message();
            response.setParam1("Very first message from client");
            response.setParam2("Param2");
            System.out.println("Message to server: " + response.getParam1());
            session.getBasicRemote().sendObject(response);
        } catch (IOException | EncodeException ex) {
            Logger.getLogger(MyClientEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnMessage
    public void message(Message message, Session session) throws IOException, EncodeException {
        System.out.println(message.getParam1());
        Message response = new Message();
        response.setParam1("From client new message");
        response.setParam2("Param2");
        session.getBasicRemote().sendObject(response);
        Client.messageLatch.countDown();
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }
}
