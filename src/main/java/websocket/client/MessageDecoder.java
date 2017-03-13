package websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message> {
    final Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    public Message decode(String jsonMessage) throws DecodeException {

        JsonObject jsonObject = Json
                .createReader(new StringReader(jsonMessage)).readObject();
        Message message = new Message();
        message.setParam1(jsonObject.getString("subject"));
        message.setParam2(jsonObject.getString("content"));
        return message;

    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            Json.createReader(new StringReader(jsonMessage)).readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        logger.info("MessageDecoder -init method called");
    }

    @Override
    public void destroy() {
        logger.info("MessageDecoder - destroy method called");
    }

}
