package com.learning.quizapp.model;

import lombok.Data;

@Data
public class QuizResponse {
    private Integer questionId;
    private String rightAnswer;
}
