package org.sachith.choicemaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.sachith.choicemaker.exception.ActiveSessionNotFoundException;
import org.sachith.choicemaker.exception.MalformedSessionFoundException;
import org.sachith.choicemaker.model.EventType;
import org.sachith.choicemaker.model.Session;
import org.sachith.choicemaker.model.dto.SocketEvent;
import org.sachith.choicemaker.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketEventController {

    private final SessionService sessionService;
    private final SimpMessagingTemplate template;

    public WebSocketEventController(SessionService sessionService, SimpMessagingTemplate template) {
        this.sessionService = sessionService;
        this.template = template;
    }

    @Operation(summary = "Rest endpoint to broadcast any event to the clients in the session " +
            "(not being used under current features)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Broadcast the event to the session",
                    content = { @Content(mediaType = "*/*",
                            schema = @Schema(implementation = Session.class)) })})
    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@Parameter(description = "Parameter with event type, user, session id and data")
                                                @RequestBody SocketEvent socketEvent) {
        template.convertAndSend("/topic/" + socketEvent.getSessionId(), socketEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This websocket endpoint will receive events from clients and
     * will broadcast to the session based on the event type (END_SESSION only)
     * @param socketEvent Parameter with event type, user, session id and data
     * @throws ActiveSessionNotFoundException Throws when active session not found
     * @throws MalformedSessionFoundException Throws Session data is malformed
     */
    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload SocketEvent socketEvent) throws ActiveSessionNotFoundException, MalformedSessionFoundException {
        if(socketEvent.getEventType().equals(EventType.END_SESSION)) {
            Session session = sessionService.terminateSession(socketEvent.getSessionId());
            sessionService.broadcastSessionTermination(session);
        }
    }

    /**
     * Web socket endpoint to communicate with other clients if needed.
     * Not being used in the restaurant selection flow current features
     * @param socketEvent Parameter with event type, user, session id and data
     * @return SocketEvent
     */
    @SendTo("/topic/{topic_id}")
    public SocketEvent broadcastMessage(@Payload SocketEvent socketEvent) {
        // client to client event broadcasting
        return socketEvent;
    }
}
