package com.github.equiz.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.equiz.questions.entries.Level;
import com.github.equiz.questions.entries.QuestionEntry;
import com.github.equiz.questions.entries.Tag;

/**
 * 
 * @author Robin
 *
 */
public class QuestionDBHelper {

	public List<QuestionEntry> getAllQuestions() throws ClassNotFoundException, SQLException {
		List<QuestionEntry> questionEntries = new ArrayList<QuestionEntry>();
		String sqlQuery = DBQueries.ALL_QUESTIONS;
		PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
		ResultSet resultSet = DBUtils.fetch(preparedStatement);
		while (resultSet.next()) {
			QuestionEntry questionEntry = new QuestionEntry();
			questionEntry.setId(resultSet.getInt("question_id"));
			questionEntry.setQuestion(resultSet.getString("question"));
			questionEntry.setTag(Tag.valueOf(resultSet.getString("tag")));
			questionEntry.setLevel(Level.valueOf(resultSet.getString("level").toUpperCase()));
			questionEntries.add(questionEntry);
		}
		return questionEntries;
	}

	public List<QuestionEntry> getAvailableQuestions() throws ClassNotFoundException, SQLException {
		List<QuestionEntry> questionEntries = new ArrayList<QuestionEntry>();
		String sqlQuery = "SELECT * FROM questions WHERE is_used='notused';";
		PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
		ResultSet resultSet = DBUtils.fetch(preparedStatement);
		while (resultSet.next()) {
			QuestionEntry questionEntry = new QuestionEntry();
			questionEntry.setId(resultSet.getInt("question_id"));
			questionEntry.setQuestion(resultSet.getString("question"));
			questionEntry.setTag(Tag.valueOf(resultSet.getString("tag")));
			questionEntry.setLevel(Level.valueOf(resultSet.getString("level").toUpperCase()));
			questionEntries.add(questionEntry);
		}
		return questionEntries;
	}

	public void addQuestions(List<QuestionEntry> questionEntries) throws SQLException, ClassNotFoundException {
		for (QuestionEntry questionEntry : questionEntries) {
			String sqlQuery = "INSERT INTO questions (question, tag, level, is_used) VALUES (?, ?, ?, ?);";
			PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
			preparedStatement.setString(1, questionEntry.getQuestion());
			preparedStatement.setString(2, questionEntry.getTag().name().toLowerCase());
			preparedStatement.setString(3, questionEntry.getLevel().name().toLowerCase());
			preparedStatement.setString(4, "notused");
			int result = DBUtils.update(preparedStatement);
		}
	}

	public void addQuestion(String question, String level, String tag) throws SQLException, ClassNotFoundException {
		String sqlQuery = "INSERT INTO questions (question, tag, level, is_used) VALUES (?, ?, ?, ?);";
		PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
		preparedStatement.setString(1, question);
		preparedStatement.setString(2, tag.toLowerCase());
		preparedStatement.setString(3, level.toLowerCase());
		preparedStatement.setString(4, "notused");
		int result = DBUtils.update(preparedStatement);
	}

	public List<QuestionEntry> getQuizQuestions(int noOfQuestions) throws SQLException, ClassNotFoundException {
		List<QuestionEntry> questionEntries = new ArrayList<QuestionEntry>();
		List<Integer> questionIds = new ArrayList<Integer>();
		String sqlQuery = "SELECT * FROM questions WHERE is_used='notused' LIMIT " + noOfQuestions + ";";
		PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
		ResultSet resultSet = DBUtils.fetch(preparedStatement);
		while (resultSet.next()) {
			QuestionEntry questionEntry = new QuestionEntry();
			questionIds.add(resultSet.getInt("question_id"));
			questionEntry.setId(resultSet.getInt("question_id"));
			questionEntry.setQuestion(resultSet.getString("question"));
			questionEntry.setTag(Tag.valueOf(resultSet.getString("tag")));
			questionEntry.setLevel(Level.valueOf(resultSet.getString("level").toUpperCase()));
			questionEntries.add(questionEntry);
		}
		QuestionDBHelper questionDBHelper = new QuestionDBHelper();
		questionDBHelper.updateQuestionUsed(questionIds, "used");
		return questionEntries;
	}

	public List<QuestionEntry> getQuizQuestionsBasedOnTag(int noOfQuestionsInEachTag)
			throws SQLException, ClassNotFoundException {
		List<QuestionEntry> questionEntries = new ArrayList<QuestionEntry>();
		List<Integer> questionIds = new ArrayList<Integer>();
		for (Tag tag : Tag.values()) {
			String sqlQuery = "SELECT * FROM questions WHERE is_used='notused' AND tag='" + tag.name() + "' LIMIT "
					+ noOfQuestionsInEachTag + ";";
			PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
			ResultSet resultSet = DBUtils.fetch(preparedStatement);
			while (resultSet.next()) {
				QuestionEntry questionEntry = new QuestionEntry();
				questionIds.add(resultSet.getInt("question_id"));
				questionEntry.setId(resultSet.getInt("question_id"));
				questionEntry.setQuestion(resultSet.getString("question"));
				questionEntry.setTag(Tag.valueOf(resultSet.getString("tag")));
				questionEntry.setLevel(Level.valueOf(resultSet.getString("level").toUpperCase()));
				questionEntries.add(questionEntry);
			}
		}
		QuestionDBHelper questionDBHelper = new QuestionDBHelper();
		questionDBHelper.updateQuestionUsed(questionIds, "used");
		return questionEntries;
	}

	public List<QuestionEntry> getQuizQuestionsBasedOnLevel(int noOfQuestionsInEachLevel)
			throws SQLException, ClassNotFoundException {
		List<QuestionEntry> questionEntries = new ArrayList<QuestionEntry>();
		List<Integer> questionIds = new ArrayList<Integer>();
		for (Level level : Level.values()) {
			String sqlQuery = "SELECT * FROM questions WHERE is_used='notused' AND level='" + level.name().toLowerCase()
					+ "' LIMIT " + noOfQuestionsInEachLevel + ";";
			PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
			ResultSet resultSet = DBUtils.fetch(preparedStatement);
			while (resultSet.next()) {
				QuestionEntry questionEntry = new QuestionEntry();
				questionIds.add(resultSet.getInt("question_id"));
				questionEntry.setId(resultSet.getInt("question_id"));
				questionEntry.setQuestion(resultSet.getString("question"));
				questionEntry.setTag(Tag.valueOf(resultSet.getString("tag")));
				questionEntry.setLevel(Level.valueOf(resultSet.getString("level").toUpperCase()));
				questionEntries.add(questionEntry);
			}
		}
		QuestionDBHelper questionDBHelper = new QuestionDBHelper();
		questionDBHelper.updateQuestionUsed(questionIds, "used");
		return questionEntries;
	}

	public List<QuestionEntry> getQuizQuestions() throws ClassNotFoundException, SQLException {
		List<QuestionEntry> questionEntries = new ArrayList<QuestionEntry>();
		QuestionDBHelper questionDBHelper = new QuestionDBHelper();
		Tag[] tags = Tag.values();
		Level[] levels = Level.values();
		int levelCount = 0;
		int tagCount = 0;
		for (int i = 0; i < 10; i++) {
			String sqlQuery = "SELECT * FROM questions WHERE is_used='notused' AND level='"
					+ levels[levelCount].name().toLowerCase() + "' AND tag='" + tags[tagCount].name().toLowerCase()
					+ "' LIMIT 1;";
			PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
			ResultSet resultSet = DBUtils.fetch(preparedStatement);
			while (resultSet.next()) {
				QuestionEntry questionEntry = new QuestionEntry();
				questionEntry.setId(resultSet.getInt("question_id"));
				questionEntry.setQuestion(resultSet.getString("question"));
				questionEntry.setTag(Tag.valueOf(resultSet.getString("tag")));
				questionEntry.setLevel(Level.valueOf(resultSet.getString("level").toUpperCase()));
				questionEntries.add(questionEntry);
				questionDBHelper.updateQuestionUsed(Collections.singletonList((resultSet.getInt("question_id"))), "used");
			}
			if (levelCount == levels.length - 1) {
				levelCount = 0;
			} else {
				levelCount++;
			}
			tagCount = tagCount == tags.length - 1 ? 0 : tagCount + 1;
		}
		return questionEntries;
	}

	public void updateQuestionUsed(List<Integer> questionIds, String isUsed)
			throws SQLException, ClassNotFoundException {
		for (Integer questionId : questionIds) {
			String sqlQuery = "UPDATE questions SET is_used=? WHERE question_id=?;";
			PreparedStatement preparedStatement = DBUtils.getConnection().prepareStatement(sqlQuery);
			preparedStatement.setString(1, isUsed);
			preparedStatement.setInt(2, questionId);
			int result = DBUtils.update(preparedStatement);
		}
	}

}
