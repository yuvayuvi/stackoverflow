package com.uv.stackoverflow.controller;


import com.uv.stackoverflow.bean.request.AnswerRequestBean;
import com.uv.stackoverflow.bean.request.QuestionRequestBean;
import com.uv.stackoverflow.service.answer.AnswerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * Created by Yuvaraj S on 14/04/22 3:58 PM.
 */
@Slf4j
@RestController
@RequestMapping(value = "/stackoverflow/answer", produces = "application/hal+json")
public class AnswerController {
    private final AnswerServiceImpl answerService;

    public AnswerController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
    }


    @PostMapping("/create")
    public ResponseEntity<Object> createAnswer(@RequestBody AnswerRequestBean answerRequestBean) {
        return  answerService.saveAnswer(answerRequestBean);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateAnswer(@PathVariable UUID id, @RequestBody AnswerRequestBean answerRequestBean) {
        return  answerService.updateAnswer(id,answerRequestBean);
    }

    @GetMapping("/upvote/{id}")
    public ResponseEntity<Object> upvoteAnswer(@PathVariable UUID id) {
        return  answerService.upvoteAnswer(id);
    }

    @GetMapping("/downvote/{id}")
    public ResponseEntity<Object> downvoteQuestion(@PathVariable UUID id) {
        return  answerService.downVoteAnswer(id);
    }

    @GetMapping("/get-by-question/{id}")
    public ResponseEntity<Object> getAllQuestions(@PathVariable UUID id) {
        return  answerService.getAllAnswers(id);
    }

}
