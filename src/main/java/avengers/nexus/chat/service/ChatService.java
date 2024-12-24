package avengers.nexus.chat.service;

import avengers.nexus.chat.dto.ChattingDto;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final SocketIOServer socketIOServer;
    public void onConnect(SocketIOClient socketIOClient){
        System.out.println("Connected");
    }
    public void onDisconnect(SocketIOClient socketIOClient){
        System.out.println("Disconnected");
    }
    public void onMessage(SocketIOClient socketIOClient, ChattingDto message, AckRequest ackRequest){
        sendToAll("message",message);
    }

    private void sendToAll(String channel,Object message){
        socketIOServer.getBroadcastOperations().sendEvent(channel,message);
    }
}
