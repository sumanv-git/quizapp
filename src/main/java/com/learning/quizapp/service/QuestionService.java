package com.learning.quizapp.service;

import com.learning.quizapp.entity.Question;
import com.learning.quizapp.model.QuestionRequest;
import com.learning.quizapp.model.QuestionResponse;
import com.learning.quizapp.respository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;
    private  final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public List<QuestionResponse> getAllQuestions() {
       List<Question> questions = questionRepository.findAll();

        return questions.stream()
                                        .map(questionMapper::toDto)
                                        .toList();
    }

    public List<QuestionResponse> getQuestionsByCategory(String category) {
        List<Question> questions = questionRepository.findByCategory(category);
        return questions.stream()
                .map(questionMapper::toDto)
                .toList();
    }

    public Integer addQuestion(QuestionRequest questionRequest) {
        Question question = QuestionMapper.toEntity(questionRequest);
        questionRepository.save(question);
        return question.getId();
    }
}
