package websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {
    final Logger logger = LoggerFactory.getLogger(MessageEncoder.class);

    @Override
    public String encode(Message message) throws EncodeException {

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("subject", message.getParam1())
                .add("content", message.getParam2()).build();
        return jsonObject.toString();

    }

    @Override
    public void init(EndpointConfig ec) {
        logger.info("MessageEncoder - init method called");
    }

    @Override
    public void destroy() {
        logger.info("MessageEncoder - destroy method called");
    }

}
