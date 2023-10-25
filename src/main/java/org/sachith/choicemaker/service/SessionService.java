package org.sachith.choicemaker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sachith.choicemaker.exception.*;
import org.sachith.choicemaker.model.EventType;
import org.sachith.choicemaker.model.Session;
import org.sachith.choicemaker.model.dto.SessionRequest;
import org.sachith.choicemaker.model.dto.SocketEvent;
import org.sachith.choicemaker.repository.SessionRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SimpMessagingTemplate template;
    private final SessionRepository sessionRepository;
    final private ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<String, Session> activeSessions = new ConcurrentHashMap<>();

    public SessionService(SimpMessagingTemplate template, SessionRepository sessionRepository) {
        this.template = template;
        this.sessionRepository = sessionRepository;
    }

    public Session createSession(SessionRequest sessionRequest) throws SessionCreationException {

        String sessionId = UUID.randomUUID().toString();
        String sessionName = sessionRequest.getSessionAdmin() + "-" +
                sessionId.replace("-", "").substring(0, 8);
        Date sessionDate = new Date(System.currentTimeMillis());
        String sessionAdmin = sessionRequest.getSessionAdmin();
        String sessionParticipants = sessionRequest.getSessionParticipants().stream()
                .map(Object::toString).collect(Collectors.joining(","));
        Session session = new Session(sessionId, sessionName, sessionDate, sessionAdmin, sessionParticipants, true);
        activeSessions.put(sessionId, session);
        try {
            session = sessionRepository.save(session);
        } catch (Exception e) {
            throw new SessionCreationException(e.getMessage());
        }
        return session;
    }

    public Session terminateSession(String sessionId) throws ActiveSessionNotFoundException {
        Session targetSession = activeSessions.remove(sessionId);
        if (targetSession != null) {
            targetSession.setIsActive(false);
            sessionRepository.save(targetSession);
        } else {
            String exMessage = "Active session not found";
            throw new ActiveSessionNotFoundException(exMessage);
        }
        return targetSession;
    }

    public void broadcastSessionTermination(Session session) throws MalformedSessionFoundException {
        String sessionData;
        try {
            sessionData = objectMapper.writeValueAsString(session);
        } catch (JsonProcessingException e) {
            throw new MalformedSessionFoundException(e.getMessage());
        }
        String sessionId = session.getSessionId();
        SocketEvent submissionEvent = new SocketEvent(EventType.END_SESSION, sessionData,
                sessionId, "admin");
        template.convertAndSend("/topic/" + sessionId, submissionEvent);
    }

    public List<Session> getAllActiveSessions(String username) {
        System.out.println(activeSessions);
        return activeSessions.values().stream()
                .filter(session -> session.getParticipants().contains(username))
                .toList();
    }

    public Session getSessionBySessionId(String sessionId) throws SessionNotFoundException {
        Session session = sessionRepository.findBySessionId(sessionId);
        if (session != null)
            return session;
        else
            throw new SessionNotFoundException("Session not found for the ID : " + sessionId);
    }
}
