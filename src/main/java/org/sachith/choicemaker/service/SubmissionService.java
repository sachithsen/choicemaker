package org.sachith.choicemaker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sachith.choicemaker.exception.MalformedSubmissionFoundException;
import org.sachith.choicemaker.exception.SocketCommunicationFailureException;
import org.sachith.choicemaker.model.EventType;
import org.sachith.choicemaker.model.Submission;
import org.sachith.choicemaker.model.dto.SocketEvent;
import org.sachith.choicemaker.model.dto.SubmissionRequest;
import org.sachith.choicemaker.repository.SubmissionRepository;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubmissionService {

    private final SimpMessagingTemplate template;
    private final SubmissionRepository submissionRepository;
    private final SessionService sessionService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SubmissionService(SimpMessagingTemplate template, SubmissionRepository submissionRepository, SessionService sessionService) {
        this.template = template;
        this.submissionRepository = submissionRepository;
        this.sessionService = sessionService;
    }

    public Submission createSubmission(SubmissionRequest submissionRequest) throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        Submission submission = submissionRepository.save(new Submission(submissionRequest.getSessionId(),
                submissionRequest.getUsername(), submissionRequest.getText(), false));
        this.broadcastSubmissionEvent(submission, EventType.ADD_CHOICE);
        return submission;
    }

    public Submission getSubmissionById(int submissionId) {
        Optional<Submission> selectedSubmission = submissionRepository.findById((long) submissionId);
        return selectedSubmission.orElse(null);
    }

    public Submission selectSubmission(int submissionId) throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        Submission selectedSubmission = getSubmissionById(submissionId);
        selectedSubmission.setSelected(true);
        submissionRepository.save(selectedSubmission);
        broadcastSubmissionEvent(selectedSubmission, EventType.END_SESSION);
        sessionService.terminateSession(selectedSubmission.getSessionId());
        return selectedSubmission;
    }

    void broadcastSubmissionEvent(Submission submission, EventType eventType) throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        String submissionData;
        try {
            submissionData = objectMapper.writeValueAsString(submission);
        } catch (JsonProcessingException e) {
            throw new MalformedSubmissionFoundException(e.getMessage());
        }
        String sessionId = submission.getSessionId();
        SocketEvent submissionEvent = new SocketEvent(eventType, submissionData,
                sessionId, submission.getUsername());
        try {
            template.convertAndSend("/topic/" + sessionId, submissionEvent);
        } catch (MessagingException e) {
            throw new SocketCommunicationFailureException(e.getMessage());
        }
    }
}
