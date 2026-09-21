package com.learning.quizapp.controller;

import com.learning.quizapp.model.QuestionRequest;
import com.learning.quizapp.model.QuestionResponse;
import com.learning.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public  ResponseEntity<List<QuestionResponse>> getQuestions(){

        List<QuestionResponse> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
        //return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    @GetMapping("category/{category}")
    //@GetMapping("category/{cat}")
    //public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(@PathVariable("cat") String category){
    public ResponseEntity<List<QuestionResponse>> getQuestionsByCategory(@PathVariable String category){
        var questions = questionService.getQuestionsByCategory(category);
        return ResponseEntity.ok(questions);
        //return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Integer> addQuestion(@RequestBody QuestionRequest questionRequest){
         Integer id = questionService.addQuestion(questionRequest);
         return ResponseEntity.ok(id);
    }
}
