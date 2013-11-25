package com.example.ReverseShootingGallery;

import java.text.SimpleDateFormat;
import java.util.*;

import android.content.SharedPreferences;

public class GameManager {

    public static final int EASY = 9001, MEDIUM = 6666, HARD = 2112, DEBUG = 1;
    public static final int PINK = 0, BLUE = 1, RAINBOW = 2;

    private static final String SCORES_KEY = "edu.mines.zfjk.ReverseShootingGallery.Scores";
    private static final String NAME_KEY = "edu.mines.zfjk.ReverseShootingGallery.Name";
    private static final String COLOR_KEY = "edu.mines.zfjk.ReverseShootingGallery.Color";
    private static final String DIFFICULTY_KEY = "edu.mines.zfjk.ReverseShootingGallery.Difficulty";
    public static final String PREFS_KEY = "edu.mines.zfjk.ReverseShootingGallery.Values";
    
    public interface GameManagerListener{
        public void updateColor();
    }
    public GameManagerListener listener;

    private int difficulty;
    private int color;
    private int shotsPerRound;
    private int currentScore;
    private String playerName;
    private int shotsLeft;
    private ArrayList<Score> scores;
    public boolean newGame = false;

    private static GameManager instance = null;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    protected GameManager() {
        this.difficulty = HARD;
        this.shotsPerRound = 10;
        this.currentScore = 0;
        this.shotsLeft = shotsPerRound;
        this.scores = new ArrayList<Score>();
        this.playerName = "ANON";
        this.color = PINK;
    }

    public void storeScore() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.US);
        String currentDate = sdf.format(new Date());
        Score score = new Score(playerName, currentScore, currentDate);
        scores.add(score);
        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score score, Score score2) {
                return -1 * (Integer.parseInt(score.score) - Integer.parseInt(score2.score));
            }
        });
        cullScores();
    }
    
    private void cullScores() {
    	while (scores.size() > 5) {
            scores.remove(scores.size() - 1);
        }
        while (scores.size() < 5) {
            scores.add(new Score("", "", ""));
        }
    }

    public void getStashedScores(SharedPreferences prefs) {
        String serializedScores = prefs.getString(SCORES_KEY, null);
        if (serializedScores == null || serializedScores.length() == 0) return;

        String[] components = serializedScores.split(":");
        for (String serializedScore : components) {
            if (serializedScores.length() > 0) {
                Score score = new Score(serializedScore);
                if (score.valid) {
                    this.scores.add(score);
                } else {
                	this.scores.add(new Score("","",""));
                }
            }
        }
        cullScores();
    }
    
    public void getStashedValues(SharedPreferences prefs) {
    	getStashedPlayerName(prefs);
    	getStashedScores(prefs);
    	getStashedColor(prefs);
    	getStashedDifficulty(prefs);
    }
    
    public void stashValues(SharedPreferences prefs) {
    	stashScores(prefs);
    	stashPlayerName(prefs);
    	stashColor(prefs);
    	stashDifficulty(prefs);
    }

    public void stashScores(SharedPreferences prefs) {
    	cullScores();
        String serializedScores = "";
        for (Score score : this.scores) {
            serializedScores = serializedScores.concat(score.serialize() + ":");
        }
        prefs.edit().putString(SCORES_KEY, serializedScores).commit();
    }

    public void setPlayerName(String name) {
        this.playerName = name.replaceAll("\\s+", "");
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
        return (this.shotsLeft <= 0);
    }

    public void resetGame() {
        this.currentScore = 0;
        this.shotsLeft = shotsPerRound;
        this.newGame = true;
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

    public List<Score> getTop5Scores() {
        return scores;
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
    
    public int getTargetColor() {
    	return color;
    }
    
    public void setTargetColor(int color) {
    	switch (color) {
    	case PINK:
    		this.color = PINK;
    		break;
    	case BLUE:
    		this.color = BLUE;
    		break;
    	case RAINBOW:
    		this.color = RAINBOW;
    		break;
    	default:
    		this.color = BLUE;
    		break;
    	}
    	listener.updateColor();
    }

	public void getStashedColor(SharedPreferences prefs) {
		this.color = prefs.getInt(COLOR_KEY, PINK);
	}
	
	public void getStashedDifficulty(SharedPreferences prefs) {
		this.difficulty = prefs.getInt(DIFFICULTY_KEY, MEDIUM);
	}
	
	public void stashColor(SharedPreferences prefs) {
		prefs.edit().putInt(COLOR_KEY, this.color).commit();
	}
	
	public void stashDifficulty(SharedPreferences prefs) {
		prefs.edit().putInt(DIFFICULTY_KEY, this.difficulty).commit();
	}
}
