package com.uv.stackoverflow.service.answer;

import com.uv.stackoverflow.bean.request.AnswerRequestBean;
import com.uv.stackoverflow.bean.response.AnswerResponseBean;
import com.uv.stackoverflow.bean.response.QuestionResponseBean;
import com.uv.stackoverflow.service.question.QuestionEntity;
import com.uv.stackoverflow.service.question.QuestionRepository;
import org.springframework.data.domain.PageRequest;
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
public class AnswerServiceImpl {
    private  final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public ResponseEntity<Object> saveAnswer(AnswerRequestBean answerRequestBean){

    AnswerEntity answerEntity =
        AnswerEntity.builder()
            .questionId(answerRequestBean.getQuestionId())
            .answer(answerRequestBean.getAnswer())
            .createBy(answerRequestBean.getCreatedBy())
            .createdAt(LocalDateTime.now())
            .votes(0L)
            .build();
        try{
            answerEntity = answerRepository.save(answerEntity);
            return getQuestionResponseBean(answerEntity);
        }catch (Exception exception){
            return  new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateAnswer(UUID id, AnswerRequestBean answerRequestBean){
        Optional<AnswerEntity> optionalAnswerEntity =  answerRepository.findById(id);

        if(optionalAnswerEntity.isPresent()){
            AnswerEntity answerEntity = optionalAnswerEntity.get();

            answerEntity.setAnswer(answerRequestBean.getAnswer());
            answerEntity.setUpdatedBy(answerRequestBean.getCreatedBy());
            answerEntity.setUpdatedAt(LocalDateTime.now());

            answerRepository.save(answerEntity);
            return getQuestionResponseBean(answerEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid answer Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> upvoteAnswer(UUID id){
        Optional<AnswerEntity> optionalAnswerEntity =  answerRepository.findById(id);

        if(optionalAnswerEntity.isPresent()){
            AnswerEntity answerEntity = optionalAnswerEntity.get();
            answerEntity.setVotes(answerEntity.getVotes() + 1);
            answerRepository.save(answerEntity);
            return getQuestionResponseBean(answerEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid answer Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> downVoteAnswer(UUID id){
        Optional<AnswerEntity> optionalAnswerEntity =  answerRepository.findById(id);

        if(optionalAnswerEntity.isPresent()){
            AnswerEntity answerEntity = optionalAnswerEntity.get();
            answerEntity.setVotes(answerEntity.getVotes() - 1);
            answerRepository.save(answerEntity);
            return getQuestionResponseBean(answerEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid answer Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllAnswers(UUID id){
        Iterable<AnswerEntity> answerEntities = answerRepository.findAllByQuestionId(id);
        return getQuestionResponseBeanList(answerEntities);
    }

    private ResponseEntity<Object> getQuestionResponseBean(AnswerEntity answerEntity) {
        QuestionEntity questionEntity =  questionRepository.findById(answerEntity.getQuestionId()).get();
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

    private ResponseEntity<Object> getQuestionResponseBeanList(Iterable<AnswerEntity> answerEntities) {
        List<AnswerResponseBean> answerResponseBeanList =  new ArrayList<>();
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

        return new ResponseEntity<>(answerResponseBeanList, HttpStatus.OK);
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
