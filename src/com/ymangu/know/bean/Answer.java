package com.ymangu.know.bean;

public class Answer {
	public static final int MM_ANSWER = 0;
	public static final int JOKE_ANSWER = 1;
	
	public int answerType;
	public String jokeAnswer;
	public int mmAnswer;
	
	
	public int getAnswerType() {
		return answerType;
	}
	public void setAnswerType(int answerType) {
		this.answerType = answerType;
	}
	public String getJokeAnswer() {
		return jokeAnswer;
	}
	public void setJokeAnswer(String jokeAnswer) {
		this.jokeAnswer = jokeAnswer;
	}
	public int getMmAnswer() {
		return mmAnswer;
	}
	public void setMmAnswer(int mmAnswer) {
		this.mmAnswer = mmAnswer;
	}
	
	
	
	
	

}
