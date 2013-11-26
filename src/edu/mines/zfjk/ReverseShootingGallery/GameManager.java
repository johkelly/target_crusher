/**
 * Description: Singleton which manages the state of the game in progress
 * @author Zach Fleischman, John Kelly
 */

package edu.mines.zfjk.ReverseShootingGallery;

import java.text.SimpleDateFormat;
import java.util.*;

import android.content.SharedPreferences;

public class GameManager {

    private static GameManager instance = null;

    public interface GameManagerListener {
        public void updateColor();
    }

    public static final int HARD = 9001, MEDIUM = 6666, EASY = 2112;
    public static final int PINK = 0, BLUE = 1, RAINBOW = 2;

    private static final String SCORES_KEY = "edu.mines.zfjk.ReverseShootingGallery.Scores";
    private static final String NAME_KEY = "edu.mines.zfjk.ReverseShootingGallery.Name";
    private static final String COLOR_KEY = "edu.mines.zfjk.ReverseShootingGallery.Color";
    private static final String DIFFICULTY_KEY = "edu.mines.zfjk.ReverseShootingGallery.Difficulty";
    public static final String PREFS_KEY = "edu.mines.zfjk.ReverseShootingGallery.Values";

    public GameManagerListener listener;

    private int difficulty;
    private int color;
    private int shotsPerRound;
    private int currentScore;
    private String playerName;
    private int shotsLeft;
    private ArrayList<Score> scores;

    public boolean newGame = false;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Hidden form external callers to enforce Singleton-ness
     */
    protected GameManager() {
        this.difficulty = HARD;
        this.shotsPerRound = 10;
        this.currentScore = 0;
        this.shotsLeft = shotsPerRound;
        this.scores = new ArrayList<Score>();
        this.playerName = "ANON";
        this.color = PINK;
    }

    /**
     * Adds a score to the set of tracked scores, and then culls scores until only 5 remain.
     * This may remove the score just added.
     */
    public void storeScore() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.US);
        String currentDate = sdf.format(new Date());
        Score score = new Score(playerName, currentScore, currentDate);
        scores.add(score);
        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score score, Score score2) {
                return -1 * (score.score - score2.score);
            }
        });
        cullScores();
    }

    /**
     * Remove scores from the Scores array until only 5 remain, beginning with the lowest score
     */
    private void cullScores() {
        while (scores.size() > 5) {
            scores.remove(scores.size() - 1);
        }
        while (scores.size() < 5) {
            scores.add(new Score("", -1, ""));
        }
    }

    /**
     * Retrieve and parse high scores from the shared preferences
     *
     * @param prefs {@code SharedPreferences} object, which should (but might not) have a high scores string stored in it
     */
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
                    Score nullScore = new Score("", -1, "");
                    nullScore.valid = false;
                    this.scores.add(nullScore);
                }
            }
        }
        cullScores();
    }

    /**
     * Aggregate state restoration call
     *
     * @param prefs {@code SharedPreferences} object to retrieve values from
     */
    public void getStashedValues(SharedPreferences prefs) {
        getStashedPlayerName(prefs);
        getStashedScores(prefs);
        getStashedColor(prefs);
        getStashedDifficulty(prefs);
    }

    /**
     * Aggregate state storage call
     *
     * @param prefs {@code SharedPreferences} object to store values into
     */
    public void stashValues(SharedPreferences prefs) {
        stashScores(prefs);
        stashPlayerName(prefs);
        stashColor(prefs);
        stashDifficulty(prefs);
    }

    /**
     * Convert scores to strings, concatenate, and store
     *
     * @param prefs {@code SharedPreferences} object to store scores into
     */
    public void stashScores(SharedPreferences prefs) {
        cullScores();
        String serializedScores = "";
        for (Score score : this.scores) {
            serializedScores = serializedScores.concat(score.serialize() + ":");
        }
        prefs.edit().putString(SCORES_KEY, serializedScores).commit();
    }

    /**
     * Change the player name, sanitizing by removing white space
     *
     * @param name Name to adopt as current player name
     */
    public void setPlayerName(String name) {
        this.playerName = name.replaceAll("\\s+", "");
    }

    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Retrieves the previous player name if possible, defaulting to ANON if not
     *
     * @param prefs {@code SharedPreferences} object to retrieve name from
     */
    public void getStashedPlayerName(SharedPreferences prefs) {
        this.playerName = prefs.getString(NAME_KEY, "ANON");
    }

    public void stashPlayerName(SharedPreferences prefs) {
        prefs.edit().putString(NAME_KEY, playerName).commit();
    }

    public void resetScores() {
        this.scores.clear();
    }

    /**
     * Predicate to check if the game is over.
     *
     * @return {@code true} if the game is over, {@code false} otherwise
     */
    public boolean gameOver() {
        return (this.shotsLeft <= 0);
    }

    /**
     * Do cleanup and reset the game state
     */
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

    /**
     * Sets the color of the displayed target object, defaulting to Blue
     *
     * @param color Color id of the color to adopt
     */
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
