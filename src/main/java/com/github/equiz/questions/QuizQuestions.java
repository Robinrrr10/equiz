package com.github.equiz.questions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.equiz.db.QuestionDBHelper;
import com.github.equiz.questions.entries.QuestionEntry;
import com.github.equiz.questions.entries.QuestionResponse;
import com.github.equiz.questions.entries.Status;
import com.github.equiz.questions.entries.StatusType;

@RestController
@RequestMapping("/api/questions")
public class QuizQuestions {
	
	QuestionDBHelper questionDBHelper = new QuestionDBHelper();

	//@GetMapping("/check")
	public String healthCheck() {
		System.out.println("in con======================");
		return "Quiz is ready";
	}
	
	@GetMapping("/all")
	public QuestionResponse getAllQuestions() throws ClassNotFoundException, SQLException{
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		status.setStatusCode(1001);
		status.setStatusMessage("Question retreived successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		List<QuestionEntry> questionEntries = questionDBHelper.getAllQuestions();
		questionResponse.setQuestionEntries(questionEntries);
		return questionResponse;
	}
	
	@GetMapping("/available")
	public QuestionResponse getAvailableQuestions() throws ClassNotFoundException, SQLException{
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		status.setStatusCode(1001);
		status.setStatusMessage("Question retreived successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		List<QuestionEntry> questionEntries = questionDBHelper.getAvailableQuestions();
		questionResponse.setQuestionEntries(questionEntries);
		return questionResponse;
	}
	
	@PostMapping("/add")
	public QuestionResponse addQuestions(@RequestBody List<QuestionEntry> entries) throws ClassNotFoundException, SQLException{
		questionDBHelper.addQuestions(entries);
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		status.setStatusCode(1001);
		status.setStatusMessage("Question added successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		QuestionEntry questionEntry = new QuestionEntry();
		questionEntry.setQuestion("Whether it is quiz");
		List<QuestionEntry> questionEntries = new ArrayList<QuestionEntry>();
		questionEntries.add(questionEntry);
		questionResponse.setQuestionEntries(questionEntries);
		return questionResponse;
	}
	
	@PostMapping("/addQuestion")
	public QuestionResponse addQuestion(@RequestParam("questionEntry") String questionEntry) throws ClassNotFoundException, SQLException{
		System.out.println("Given:"+questionEntry);
		String[] question = questionEntry.split("\\|");
		questionDBHelper.addQuestion(question[0], question[1], question[2]);
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		status.setStatusCode(1001);
		status.setStatusMessage("Question added successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		return questionResponse;
	}
	
	@PutMapping("/quizQuestions")
	public QuestionResponse getQuizQuestions() throws ClassNotFoundException, SQLException{
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		status.setStatusCode(1001);
		status.setStatusMessage("Question added successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		int noOfQuestions = 3;  //default 10
		List<QuestionEntry> questionEntries = questionDBHelper.getQuizQuestions(noOfQuestions);
		questionResponse.setQuestionEntries(questionEntries);
		return questionResponse;
	} 
	
	@PutMapping("/quizQuestionsBasedOnTag")
	public QuestionResponse getQuizQuestionsBasedOnTag() throws ClassNotFoundException, SQLException{
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		status.setStatusCode(1001);
		status.setStatusMessage("Question added successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		int noOfQuestionsInEachTag = 1;  //default 1
		List<QuestionEntry> questionEntries = questionDBHelper.getQuizQuestionsBasedOnTag(noOfQuestionsInEachTag);
		questionResponse.setQuestionEntries(questionEntries);
		return questionResponse;
	} 
	
	@PutMapping("/quizQuestionsBasedOnLevel")
	public QuestionResponse getQuizQuestionsBasedOnLevel() throws ClassNotFoundException, SQLException{
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		status.setStatusCode(1001);
		status.setStatusMessage("Question added successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		int noOfQuestionsInEachLevel = 2;  //default 2
		List<QuestionEntry> questionEntries = questionDBHelper.getQuizQuestionsBasedOnLevel(noOfQuestionsInEachLevel);
		questionResponse.setQuestionEntries(questionEntries);
		return questionResponse;
	} 
}
