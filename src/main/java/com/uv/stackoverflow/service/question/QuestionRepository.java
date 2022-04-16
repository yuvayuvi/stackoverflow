package com.uv.stackoverflow.service.question;

import com.uv.stackoverflow.service.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<QuestionEntity, UUID> {
}