package org.sachith.choicemaker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sachith.choicemaker.exception.SessionCreationException;
import org.sachith.choicemaker.exception.SessionNotFoundException;
import org.sachith.choicemaker.model.Session;
import org.sachith.choicemaker.model.dto.SessionRequest;
import org.sachith.choicemaker.model.dto.SummaryRecord;
import org.sachith.choicemaker.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSession() throws SessionCreationException {
        SessionRequest sessionRequest = new SessionRequest("testAdmin", List.of("testAdmin", "testUser1", "testUser2"));
        Session sessionResponse = new Session("26f3c84a-73b0-11ee-b962-0242ac120002", "testAdmin-26f3c84a",
                new Date(System.currentTimeMillis()), "testAdmin", List.of("testAdmin", "testUser1", "testUser2").toString(), true);
        Mockito.when(sessionService.createSession(sessionRequest)).thenReturn(sessionResponse);

        ResponseEntity<Session> responseEntity = sessionController.createSession(sessionRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllActiveSessionsByUser() {
        String username = "testUser";
        List<Session> sessions = new ArrayList<>();
        Mockito.when(sessionService.getAllActiveSessions(username)).thenReturn(sessions);

        ResponseEntity<List<Session>> responseEntity = sessionController.getAllActiveSessionsByUser(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetSessionsById() throws SessionNotFoundException {
        String sessionId = "26f3c84a-73b0-11ee-b962-0242ac120002";
        Session session = new Session("26f3c84a-73b0-11ee-b962-0242ac120002", "testAdmin-26f3c84a",
                new Date(System.currentTimeMillis()), "testAdmin", List.of("testAdmin", "testUser1", "testUser2").toString(), true);
        Mockito.when(sessionService.getSessionBySessionId(sessionId)).thenReturn(session);

        ResponseEntity<Session> responseEntity = sessionController.getSessionsById(sessionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testExceptionGetSessionsById() throws SessionNotFoundException {
        String sessionId = "nonExistentSessionId";
        Mockito.when(sessionService.getSessionBySessionId(sessionId)).thenThrow(new SessionNotFoundException("Session not found"));

        assertThrows(SessionNotFoundException.class, () -> {
            sessionController.getSessionsById(sessionId);
        });
    }

    @Test
    void testGetAllSessionsByUser() {
        String username = "testUser";
        List<SummaryRecord> summarySessions = new ArrayList<>();
        Mockito.when(sessionService.getAllSessionSummary(username)).thenReturn(summarySessions);

        ResponseEntity<List<SummaryRecord>> responseEntity = sessionController.getAllSessionsByUser(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}

