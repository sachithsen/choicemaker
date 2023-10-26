package org.sachith.choicemaker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sachith.choicemaker.exception.SessionNotFoundException;
import org.sachith.choicemaker.model.Session;
import org.sachith.choicemaker.repository.SessionRepository;
import org.sachith.choicemaker.repository.SubmissionRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SimpMessagingTemplate template;

    @Mock
    private SubmissionRepository submissionRepository;

    @BeforeEach
    void setUp() {
        sessionService = new SessionService(template, sessionRepository, submissionRepository);
    }

    @Test
    void testGetSessionBySessionId_SessionFound() throws SessionNotFoundException {
        String sessionId = "26f3c84a-73b0-11ee-b962-0242ac120002";
        Session session = new Session(sessionId, "testAdmin-26f3c84a",
                new Date(System.currentTimeMillis()), "testAdmin", List.of("testAdmin", "testUser1", "testUser2").toString(), true);
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(session);

        Session result = sessionService.getSessionBySessionId(sessionId);

        assertEquals(session, result);
        assertEquals(session.getSessionId(), result.getSessionId());
    }

    @Test
    void testGetSessionBySessionId_SessionNotFound() {
        String sessionId = "nonExistentSessionId";
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(null);

        assertThrows(SessionNotFoundException.class, () -> sessionService.getSessionBySessionId(sessionId));
    }
}

