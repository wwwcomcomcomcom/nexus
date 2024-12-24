package avengers.nexus.chat.controller;

import avengers.nexus.chat.dto.ChattingDto;
import avengers.nexus.chat.service.ChatService;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
public class ChattingController {
    private final SocketIOServer socketIOServer;
    private final ChatService chatService;
    public ChattingController(SocketIOServer socketIOServer, ChatService chatService) {
        this.socketIOServer = socketIOServer;
        this.chatService = chatService;
        socketIOServer.addConnectListener(chatService::onConnect);
        socketIOServer.addDisconnectListener(chatService::onDisconnect);
        socketIOServer.addEventListener("message", ChattingDto.class, chatService::onMessage);
    }
}
