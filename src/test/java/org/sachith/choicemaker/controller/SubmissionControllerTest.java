package org.sachith.choicemaker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sachith.choicemaker.exception.MalformedSubmissionFoundException;
import org.sachith.choicemaker.exception.SocketCommunicationFailureException;
import org.sachith.choicemaker.model.Submission;
import org.sachith.choicemaker.model.dto.SubmissionRequest;
import org.sachith.choicemaker.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SubmissionControllerTest {

    @InjectMocks
    private SubmissionController submissionController;

    @Mock
    private SubmissionService submissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubmission() throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        SubmissionRequest submissionRequest = new SubmissionRequest("testSessionId", "testUser", "Test Restaurant");
        Submission submission = new Submission("testSessionId", "testUser", "Test Restaurant", false);
        when(submissionService.createSubmission(submissionRequest)).thenReturn(submission);

        ResponseEntity<Submission> responseEntity = submissionController.createSubmission(submissionRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testSelectSubmission() throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        int submissionId = 123;
        Submission submission = new Submission("testSessionId", "testUser", "Test Restaurant", false);
        when(submissionService.selectSubmission(submissionId)).thenReturn(submission);

        ResponseEntity<Submission> responseEntity = submissionController.selectSubmission(submissionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
