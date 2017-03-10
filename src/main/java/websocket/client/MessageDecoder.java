package websocket.client;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message> {

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
        System.out.println("MessageDecoder -init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageDecoder - destroy method called");
    }

}
