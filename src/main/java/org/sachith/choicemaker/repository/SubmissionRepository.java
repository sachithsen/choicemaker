package org.sachith.choicemaker.repository;

import org.sachith.choicemaker.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findAllBySessionId(String sessionId);
}
