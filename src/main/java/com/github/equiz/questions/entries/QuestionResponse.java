package com.github.equiz.questions.entries;

import java.util.List;

public class QuestionResponse {
	
	private Status status;
	private List<QuestionEntry> questionEntries;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<QuestionEntry> getQuestionEntries() {
		return questionEntries;
	}
	public void setQuestionEntries(List<QuestionEntry> questionEntries) {
		this.questionEntries = questionEntries;
	}
}
