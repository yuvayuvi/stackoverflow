package com.uv.stackoverflow.bean.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Yuvaraj S on 15/04/22 3:55 PM.
 */
@Data
@Builder
public class AnswerResponseBean {
    private UUID id;
    private String answer;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private Long votes;
}
