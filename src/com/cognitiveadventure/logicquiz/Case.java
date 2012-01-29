package com.cognitiveadventure.logicquiz;

public class Case {
	String path;
	boolean answer;
	
	Case(String path)
	{
		this.path = path;
		answer = path.contains("true");
	}
}
