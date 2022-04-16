package com.uv.stackoverflow.service.question;

import com.uv.stackoverflow.bean.request.QuestionRequestBean;
import com.uv.stackoverflow.bean.response.AnswerResponseBean;
import com.uv.stackoverflow.bean.response.QuestionResponseBean;
import com.uv.stackoverflow.service.answer.AnswerEntity;
import com.uv.stackoverflow.service.answer.AnswerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Yuvaraj S on 15/04/22 1:09 PM.
 */
@Service
public class QuestionServiceImpl {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public ResponseEntity<Object> saveQuestion(QuestionRequestBean questionRequestBean){

    QuestionEntity questionEntity =
        QuestionEntity.builder()
            .title(questionRequestBean.getTitle())
            .body(questionRequestBean.getBody())
            .createBy(questionRequestBean.getCreatedBy())
            .createdAt(LocalDateTime.now())
            .votes(0L)
            .build();
        try{
            questionEntity = questionRepository.save(questionEntity);
            return getQuestionResponseBean(questionEntity);
        }catch (Exception exception){
            return  new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateQuestion(UUID id, QuestionRequestBean questionRequestBean){
        Optional<QuestionEntity> optionalQuestionEntity =  questionRepository.findById(id);

        if(optionalQuestionEntity.isPresent()){
            QuestionEntity questionEntity = optionalQuestionEntity.get();

            questionEntity.setTitle(questionRequestBean.getTitle());
            questionEntity.setBody(questionRequestBean.getBody());
            questionEntity.setUpdatedBy(questionRequestBean.getCreatedBy());
            questionEntity.setUpdatedAt(LocalDateTime.now());

            questionRepository.save(questionEntity);
            return getQuestionResponseBean(questionEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid question Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> upvoteQuestion(UUID id){
        Optional<QuestionEntity> optionalQuestionEntity =  questionRepository.findById(id);

        if(optionalQuestionEntity.isPresent()){
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            questionEntity.setVotes(questionEntity.getVotes() + 1);
            questionRepository.save(questionEntity);
            return getQuestionResponseBean(questionEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid question Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> downVoteQuestion(UUID id){
        Optional<QuestionEntity> optionalQuestionEntity =  questionRepository.findById(id);

        if(optionalQuestionEntity.isPresent()){
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            questionEntity.setVotes(questionEntity.getVotes() - 1);
            questionRepository.save(questionEntity);
            return getQuestionResponseBean(questionEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid question Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getQuestionDetails(UUID id){
        Optional<QuestionEntity> optionalQuestionEntity =  questionRepository.findById(id);
        if(optionalQuestionEntity.isPresent()){
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            return getQuestionResponseBean(questionEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid question Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllQuestionDetails(Integer page, Integer limit){
        Iterable<QuestionEntity> questionEntities = questionRepository.findAll(PageRequest.of(page, limit));
        return getQuestionResponseBeanList(questionEntities);
    }

    private ResponseEntity<Object> getQuestionResponseBean(QuestionEntity questionEntity) {
        List<AnswerResponseBean> answerResponseBeanList = getAnswerResponseBeans(questionEntity);
        QuestionResponseBean questionResponseBean = new QuestionResponseBean();
        questionResponseBean.setId(questionEntity.getId());
        questionResponseBean.setTitle(questionEntity.getTitle());
        questionResponseBean.setBody(questionEntity.getBody());
        questionResponseBean.setCreatedBy(questionEntity.getCreateBy());
        questionResponseBean.setCreatedAt(questionEntity.getCreatedAt());
        questionResponseBean.setUpdatedBy(questionEntity.getUpdatedBy());
        questionResponseBean.setUpdatedAt(questionEntity.getUpdatedAt());
        questionResponseBean.setVotes(questionEntity.getVotes());
        questionResponseBean.setAnswers(answerResponseBeanList);
        return new ResponseEntity<>(questionResponseBean, HttpStatus.OK);
    }

    private ResponseEntity<Object> getQuestionResponseBeanList(Iterable<QuestionEntity> questionEntities) {
        List<QuestionResponseBean> questionResponseBeanList =  new ArrayList<>();
        questionEntities.forEach(questionEntity -> {
            List<AnswerResponseBean> answerResponseBeanList = getAnswerResponseBeans(questionEntity);
            QuestionResponseBean questionResponseBean = new QuestionResponseBean();
            questionResponseBean.setId(questionEntity.getId());
            questionResponseBean.setTitle(questionEntity.getTitle());
            questionResponseBean.setBody(questionEntity.getBody());
            questionResponseBean.setCreatedBy(questionEntity.getCreateBy());
            questionResponseBean.setCreatedAt(questionEntity.getCreatedAt());
            questionResponseBean.setUpdatedBy(questionEntity.getUpdatedBy());
            questionResponseBean.setUpdatedAt(questionEntity.getUpdatedAt());
            questionResponseBean.setVotes(questionEntity.getVotes());
            questionResponseBean.setAnswers(answerResponseBeanList);
            questionResponseBeanList.add(questionResponseBean);
        });

        return new ResponseEntity<>(questionResponseBeanList, HttpStatus.OK);
    }

    private List<AnswerResponseBean> getAnswerResponseBeans(QuestionEntity questionEntity) {
        List<AnswerEntity> answerEntities = answerRepository.findAllByQuestionId(questionEntity.getId());
        List<AnswerResponseBean> answerResponseBeanList = new ArrayList<>();
        answerEntities.forEach(answerEntity -> {
            AnswerResponseBean answerResponseBean =  AnswerResponseBean.builder()
                    .id(answerEntity.getId())
                    .answer(answerEntity.getAnswer())
                    .createdBy(answerEntity.getCreateBy())
                    .createdAt(answerEntity.getCreatedAt())
                    .updatedBy(answerEntity.getUpdatedBy())
                    .updatedAt(answerEntity.getUpdatedAt())
                    .votes(answerEntity.getVotes())
                    .build();
            answerResponseBeanList.add(answerResponseBean);
        });
        return answerResponseBeanList;
    }
}
