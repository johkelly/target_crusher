package com.example.ReverseShootingGallery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.SharedPreferences;

public class GameManager {

	public static int EASY=9001, MEDIUM=6666, HARD=2112;
	
	private static String SCORES_KEY = "edu.mines.zfjk.ReverseShootingGallery.Scores";
	private static String NAME_KEY = "edu.mines.zfjk.ReverseShootingGallery.Scores";
	
	private int difficulty;
	private int shotsPerRound;
	private int currentScore;
	private String playerName;
	private int shotsLeft;
	private ArrayList<Score> scores;
	
	private static GameManager instance = null;

	public static GameManager getInstance() {
	   if(instance == null) {
	      instance = new GameManager();
	   }
	   return instance;
	}
	
	protected GameManager() {
		this.difficulty = MEDIUM;
		this.shotsPerRound = 10;
		this.currentScore = 0;
		this.shotsLeft = shotsPerRound;
		this.scores = new ArrayList<Score>();
		this.playerName = "ANON";
	}
	
	public void storeScore() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.US);
		String currentDate = sdf.format(new Date());
		Score score = new Score(playerName, currentScore, currentDate);
		scores.add(score);
	}
	
	public void getStashedScores(SharedPreferences prefs) {
		String serializedScores = prefs.getString(SCORES_KEY, null);
		if (serializedScores == null || serializedScores == "") return;
		
		String[] components = serializedScores.split(":");
		for (String serializedScore : components) {
			if (serializedScore != "") {
				Score score = new Score(serializedScore);
				this.scores.add(score);
			}
		}
	}
	
	public void stashScores(SharedPreferences prefs) {
		String serializedScores = "";
		for (Score score : this.scores) {
			serializedScores = serializedScores.concat(score.serialize() + ":");
		}
		prefs.edit().putString(SCORES_KEY, serializedScores).commit();
	}
	
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public void getStashedPlayerName(SharedPreferences prefs) {
		this.playerName = prefs.getString(NAME_KEY, "ANON");
	}
	
	public void stashPlayerName(SharedPreferences prefs) {
		prefs.edit().putString(NAME_KEY, playerName).commit();
	}
	
	public void resetScores() {
		this.scores.clear();
	}
	
	public boolean gameOver() {
		return (this.shotsLeft == 0);
	}
	
	public void resetGame() {
		this.currentScore = 0;
		this.shotsLeft = shotsPerRound;
	}
	
	public void targetHit() {
		this.currentScore++;
		this.shotsLeft--;
	}
	
	public void targetMiss() {
		this.shotsLeft--;
	}
	
	public int getScore() {
		return this.currentScore;
	}
	
	public int getShotsLeft() {
		return this.shotsLeft;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = Math.max(500, difficulty);
	}
	
	public int shotDelay() {
		return this.difficulty;
	}
}