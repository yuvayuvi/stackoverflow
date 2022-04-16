package com.uv.stackoverflow.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Yuvaraj S on 14/04/22 3:55 PM.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequestBean implements Serializable {
    UUID questionId;
    String answer;
    String createdBy;
}
