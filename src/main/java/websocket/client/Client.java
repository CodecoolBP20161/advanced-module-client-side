package websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import javax.xml.bind.DatatypeConverter;

import static java.util.Arrays.asList;

public class Client {

    final static CountDownLatch messageLatch = new CountDownLatch(1);
    final static Logger logger = LoggerFactory.getLogger(MyClientEndpoint.class);
    final static String credentials = "userA:abc123";
    final static String uri = "ws://192.168.161.94:8080/advanced_module_banktech_war_exploded/websocket";



    public static void main(String[] args) {
        ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {
            public void beforeRequest(Map<String, List<String>> headers) {
                headers.put("Authorization", asList("Basic " + DatatypeConverter.printBase64Binary(credentials.getBytes())));
            }
        };

        ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create()
                .configurator(configurator)
                .build();
        try {
            logger.info("Connecting to " + uri);
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(new MyClientEndpoint(), clientConfig,  URI.create(uri));
            messageLatch.await(50, TimeUnit.SECONDS);
        } catch (DeploymentException | InterruptedException | IOException ex) {
            logger.info(Client.class.getName(), ex);
        }
    }
}
