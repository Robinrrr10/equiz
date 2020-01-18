package com.github.equiz.questions;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.github.equiz.db.QuestionDBHelper;
import com.github.equiz.questions.entries.QuestionEntry;
import com.github.equiz.questions.entries.QuestionResponse;
import com.github.equiz.questions.entries.Status;
import com.github.equiz.questions.entries.StatusType;

/**
 * Contains all API's related to quiz questions
 * 
 * @author Robin
 *
 */
@RestController
@RequestMapping("/api/questions")
public class QuizQuestions {

	QuestionDBHelper questionDBHelper = new QuestionDBHelper();

	/**
	 * This is health checking api
	 * 
	 * @return
	 */
	@GetMapping("/healthCheck")
	public String healthCheck() {
		return "Quiz is ready";
	}

	/**
	 * This method will return all questions
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@GetMapping("/all")
	public QuestionResponse getAllQuestions() throws ClassNotFoundException, SQLException {
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
			List<QuestionEntry> questionEntries = questionDBHelper.getAllQuestions();
			status.setStatusCode(1001);
			status.setStatusMessage("All Questions retreived successfully");
			status.setStatusType(StatusType.SUCCESS);
			questionResponse.setStatus(status);
			questionResponse.setQuestionEntries(questionEntries);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2001);
			status.setStatusMessage("Error while fetching questions from database");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}

	/**
	 * This method will return all available questions which are not used
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@GetMapping("/available")
	public QuestionResponse getAvailableQuestions() throws ClassNotFoundException, SQLException {
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
		List<QuestionEntry> questionEntries = questionDBHelper.getAvailableQuestions();
		status.setStatusCode(1002);
		status.setStatusMessage("Retreived available questios successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		questionResponse.setQuestionEntries(questionEntries);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2002);
			status.setStatusMessage("Error while retreiving available questions in database");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}

	/**
	 * This api method is used to add/create new questions.
	 * Here we have to pass questions in payload
	 * @param questionEntries
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@PostMapping("/add")
	public QuestionResponse addQuestions(@RequestBody List<QuestionEntry> questionEntries)
			throws ClassNotFoundException, SQLException {
		questionDBHelper.addQuestions(questionEntries);
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
		status.setStatusCode(1003);
		status.setStatusMessage("Question added successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2003);
			status.setStatusMessage("Error while adding new questions in database");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}

	/**
	 * This api method is used to add single question using query param
	 * Pass query param like below
	 * give question here|level|tag
	 * Eg:
	 * What is java?|HARD|tag4
	 * @param questionEntry
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@PostMapping("/addQuestion")
	public QuestionResponse addQuestion(@RequestParam("questionEntry") String questionEntry)
			throws ClassNotFoundException, SQLException {
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
		String[] question = questionEntry.split("\\|");
		questionDBHelper.addQuestion(question[0], question[1], question[2]);
		status.setStatusCode(1004);
		status.setStatusMessage("One Question added successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2004);
			status.setStatusMessage("Error while adding a question");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}

	/**
	 * This api method is used upload set of questions using multipart file in api
	 * @param file
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	@PostMapping("/uploadQuestions")
	public QuestionResponse uploadQuestionsUsingFiles(@RequestParam("file") MultipartFile file)
			throws ClassNotFoundException, SQLException, IOException {
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
		QuizQuestionHelper quizQuestionHelper = new QuizQuestionHelper();
		List<String> questionEntries = quizQuestionHelper.getAllLinesInList(file);
		for (String questionEntry : questionEntries) {
			String[] question = questionEntry.split("\\|");
			questionDBHelper.addQuestion(question[0], question[1], question[2]);
		}
		status.setStatusCode(1005);
		status.setStatusMessage("Questions uploaded successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		}catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2005);
			status.setStatusMessage("Error while uploading questions");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}

	/**
	 * This method will return quiz questions based on all criteria
	 * It will return totally 10 questions which contains atleast 2 questions from each level and atleast 1 questions from each tag
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@PutMapping("/quizQuestions")
	public QuestionResponse getQuizQuestions() throws ClassNotFoundException, SQLException {
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
		List<QuestionEntry> questionEntries = questionDBHelper.getQuizQuestions();
		status.setStatusCode(1006);
		status.setStatusMessage("Quiz question retreived");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		questionResponse.setQuestionEntries(questionEntries);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2006);
			status.setStatusMessage("Error while retreiving quiz questions");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}

	/**
	 * This api method will return 10 quiz  questions
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@PutMapping("/quizQuestionsTen")
	public QuestionResponse getRandomTenQuizQuestions() throws ClassNotFoundException, SQLException {
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
		List<QuestionEntry> questionEntries = questionDBHelper.getQuizQuestions(10); //
		status.setStatusCode(1007);
		status.setStatusMessage("10 Quiz question retreived successfully");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		questionResponse.setQuestionEntries(questionEntries);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2007);
			status.setStatusMessage("Error while retreiving quiz questions");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}
	
	/**
	 * This api method will return 1 question from each tag
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@PutMapping("/quizQuestionsBasedOnTag")
	public QuestionResponse getQuizQuestionsBasedOnTag() throws ClassNotFoundException, SQLException {
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
		int noOfQuestionsInEachTag = 1; // default 1
		List<QuestionEntry> questionEntries = questionDBHelper.getQuizQuestionsBasedOnTag(noOfQuestionsInEachTag);
		questionResponse.setQuestionEntries(questionEntries);
		status.setStatusCode(1008);
		status.setStatusMessage("Question retreived successfully based on tag");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2008);
			status.setStatusMessage("Error while retreiving questions based on tag");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}

	/**
	 * This api method will return 2 question from each level
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@PutMapping("/quizQuestionsBasedOnLevel")
	public QuestionResponse getQuizQuestionsBasedOnLevel() throws ClassNotFoundException, SQLException {
		QuestionResponse questionResponse = new QuestionResponse();
		Status status = new Status();
		try {
		int noOfQuestionsInEachLevel = 2; // default 2
		List<QuestionEntry> questionEntries = questionDBHelper.getQuizQuestionsBasedOnLevel(noOfQuestionsInEachLevel);
		questionResponse.setQuestionEntries(questionEntries);
		status.setStatusCode(1009);
		status.setStatusMessage("Question retreived successfully based on level");
		status.setStatusType(StatusType.SUCCESS);
		questionResponse.setStatus(status);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			status.setStatusCode(2009);
			status.setStatusMessage("Error while retreiving questions based on level");
			status.setStatusType(StatusType.ERROR);
			questionResponse.setStatus(status);
		}
		return questionResponse;
	}
}
