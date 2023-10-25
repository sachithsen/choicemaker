package org.sachith.choicemaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.sachith.choicemaker.exception.SessionCreationException;
import org.sachith.choicemaker.exception.SessionNotFoundException;
import org.sachith.choicemaker.model.Session;
import org.sachith.choicemaker.model.dto.SessionRequest;
import org.sachith.choicemaker.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @Operation(summary = "Create a session by given data about the session and making session available to join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created the session and returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Session.class)) }),
            @ApiResponse(responseCode = "500", description = "Session creation failed due to server error",
                    content = { @Content(mediaType = "*/*",
                            schema = @Schema(implementation = String.class)) })})
    @PostMapping("/session")
    public ResponseEntity<Session> createSession(@Parameter(description = "Session data for creating the session")
                                                     @RequestBody SessionRequest sessionRequest)
            throws SessionCreationException {
        Session sessionResponse = sessionService.createSession(sessionRequest);
        return ResponseEntity.ok(sessionResponse);
    }

    @Operation(summary = "Get all the active sessions for a user. User should be a " +
            "participant of the session when it creates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve list of active sessions",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) })})
    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getAllActiveSessionsByUser(@Parameter(description = "Username of user")
                                                                        @RequestParam String username) {
        List<Session> sessions = sessionService.getAllActiveSessions(username);
        return ResponseEntity.ok(sessions);
    }

    @Operation(summary = "Get the session bu sessionId given")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session found and returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Session.class)) }),
            @ApiResponse(responseCode = "404", description = "Session not found for the ID given",
                    content = { @Content(mediaType = "*/*",
                            schema = @Schema(implementation = String.class)) })})
    @GetMapping("/session")
    public ResponseEntity<Session> getSessionsById(@Parameter(description = "Session ID to retrieve the session")
                                                       @RequestParam String sessionId) throws SessionNotFoundException {
        Session session = sessionService.getSessionBySessionId(sessionId);
        return ResponseEntity.ok(session);
    }
}
