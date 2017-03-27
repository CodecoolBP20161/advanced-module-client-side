package websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

public class Client {

    final static CountDownLatch messageLatch = new CountDownLatch(1);
    final static Logger logger = LoggerFactory.getLogger(MyClientEndpoint.class);

    public static void main(String[] args) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://192.168.161.131:8080/advanced_module_banktech_war_exploded/websocket";
            logger.info("Connecting to " + uri);
            container.connectToServer(MyClientEndpoint.class, URI.create(uri));
            messageLatch.await(50, TimeUnit.SECONDS);
        } catch (DeploymentException | InterruptedException | IOException ex) {
            logger.info(Client.class.getName(), ex);
        }
    }
}
