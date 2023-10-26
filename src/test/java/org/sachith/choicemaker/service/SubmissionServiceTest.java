package org.sachith.choicemaker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sachith.choicemaker.exception.MalformedSubmissionFoundException;
import org.sachith.choicemaker.exception.SocketCommunicationFailureException;
import org.sachith.choicemaker.model.EventType;
import org.sachith.choicemaker.model.Session;
import org.sachith.choicemaker.model.Submission;
import org.sachith.choicemaker.model.dto.SubmissionRequest;
import org.sachith.choicemaker.repository.SubmissionRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubmissionServiceTest {

    @InjectMocks
    private SubmissionService submissionService;

    @Mock
    private SimpMessagingTemplate template;

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private SessionService sessionService;

    @Test
    void testCreateSubmission() throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        SubmissionRequest submissionRequest = new SubmissionRequest("testSessionId", "testUser", "Test Restaurant");
        Submission submission = new Submission("testSessionId", "testUser", "Test Restaurant", false);
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);
        doNothing().when(template).convertAndSend(anyString());

        Submission createdSubmission = submissionService.createSubmission(submissionRequest);

        assertNotNull(createdSubmission);
    }

    @Test
    void testGetSubmissionById() {
        int submissionId = 123;
        Submission submission = new Submission("testSessionId", "testUser", "Test Restaurant", false);
        Optional<Submission> optionalSubmission = Optional.of(submission);
        when(submissionRepository.findById(anyLong())).thenReturn(optionalSubmission);

        Submission retrievedSubmission = submissionService.getSubmissionById(submissionId);

        assertNotNull(retrievedSubmission);
    }

    @Test
    void testSelectSubmission() throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        int submissionId = 123;
        Submission submission = new Submission("testSessionId", "testUser", "Test Restaurant", false);
        Session session = new Session("sessionId", "testAdmin-26f3c84a",
                new Date(System.currentTimeMillis()), "testAdmin", List.of("testAdmin", "testUser1", "testUser2").toString(), true);
        when(submissionRepository.findById(anyLong())).thenReturn(Optional.of(submission));
        when(sessionService.terminateSession(anyString())).thenReturn(session);
        doNothing().when(template).convertAndSend(anyString());

        Submission selectedSubmission = submissionService.selectSubmission(submissionId);

        assertNotNull(selectedSubmission);
        assertTrue(selectedSubmission.isSelected());
    }
}

