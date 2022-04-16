package com.uv.stackoverflow.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Yuvaraj S on 14/04/22 3:55 PM.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestBean implements Serializable {
    String title;
    String body;
    String createdBy;
}
