package com.example.ReverseShootingGallery;

public class Score {
	public String name;
	public int score;
	public String date;
	
	public Score(String name, int score, String date) {
		this.name = name;
		this.score = score;
		this.date = date;
	}
	
	public Score(String serializedScore) {
		String[] components = serializedScore.split(";");
		this.name = components[0];
		this.score = Integer.parseInt(components[1]);
		this.date = components[2];
	}
	
	public String serialize() {
		return name + ";" + score + ";" + date;
	}
}
