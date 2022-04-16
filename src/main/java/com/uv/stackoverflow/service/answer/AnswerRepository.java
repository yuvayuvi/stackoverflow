package com.uv.stackoverflow.service.answer;

import com.uv.stackoverflow.service.question.QuestionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {
    List<AnswerEntity> findAllByQuestionId(UUID id);
}