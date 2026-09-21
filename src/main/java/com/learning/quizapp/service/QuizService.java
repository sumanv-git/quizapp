package com.learning.quizapp.service;

import com.learning.quizapp.entity.Question;
import com.learning.quizapp.entity.Quiz;
import com.learning.quizapp.model.QuestionResponse;
import com.learning.quizapp.model.QuizResponse;
import com.learning.quizapp.respository.QuestionRepository;
import com.learning.quizapp.respository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public Integer createQuiz(String category, int numQ, String title) {
        log.info("Creating quiz with category: {}, title: {}, numQuestions: {}", category, title, numQ);
        List<Question> questions = questionRepository.findRandonQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setCategory(category);
        quiz.setNumQuestions(numQ);
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        return quizRepository.save(quiz).getId();
    }

    public List<QuestionResponse> getAllQuestions(Integer id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        return quiz.getQuestions().stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    public Integer submitQuiz(Integer id, List<QuizResponse> quizResponses) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<Question> quizQuestions = quiz.getQuestions();

        int correctAnswers = 0;
        for (QuizResponse quizResponse : quizResponses) {
            Question question = quizQuestions.stream()
                    .filter(q -> q.getId().equals(quizResponse.getQuestionId()))
                    .findFirst()
                    .orElse(null);
            if (question != null && question.getRightAnswer().equals(quizResponse.getRightAnswer()))
                correctAnswers++;
        }
        return correctAnswers;

    }
}
