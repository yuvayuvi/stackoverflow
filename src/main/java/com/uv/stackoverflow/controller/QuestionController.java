package com.uv.stackoverflow.controller;


import com.uv.stackoverflow.bean.request.QuestionRequestBean;
import com.uv.stackoverflow.service.question.QuestionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * Created by Yuvaraj S on 14/04/22 3:58 PM.
 */
@Slf4j
@RestController
@RequestMapping(value = "/stackoverflow/question", produces = "application/hal+json")
public class QuestionController {
    private final QuestionServiceImpl questionService;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }


    @PostMapping("/create")
    public ResponseEntity<Object> createQuestion(@RequestBody QuestionRequestBean questionRequestBean) {
        return  questionService.saveQuestion(questionRequestBean);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateQuestion(@PathVariable UUID id, @RequestBody QuestionRequestBean questionRequestBean) {
        return  questionService.updateQuestion(id, questionRequestBean);
    }

    @GetMapping("/upvote/{id}")
    public ResponseEntity<Object> upvoteQuestion(@PathVariable UUID id) {
        return  questionService.upvoteQuestion(id);
    }

    @GetMapping("/downvote/{id}")
    public ResponseEntity<Object> downvoteQuestion(@PathVariable UUID id) {
        return  questionService.downVoteQuestion(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getQuestion(@PathVariable UUID id) {
        return  questionService.getQuestionDetails(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllQuestions(@RequestParam Integer page, @RequestParam Integer limit) {
        return  questionService.getAllQuestionDetails(page, limit);
    }

}
