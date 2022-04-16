package com.uv.stackoverflow.bean.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yuvaraj S on 15/04/22 3:55 PM.
 */
@Data
public class QuestionResponseBean {
    private UUID id;
    private String title;
    private String body;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private Long votes;
    private List<AnswerResponseBean> answers;
}
