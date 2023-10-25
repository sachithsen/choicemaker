package org.sachith.choicemaker.repository;

import org.sachith.choicemaker.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findBySessionId(String sessionId);
}
