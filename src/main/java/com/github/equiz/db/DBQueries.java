package com.github.equiz.db;

public interface DBQueries {

	public static String ALL_QUESTIONS = "SELECT * FROM questions;";
	public static String QUIZ_QUESTIONS = "SELECT * FROM questions WHERE status=? AND TAG=? LIMIT ?;";
	
}
