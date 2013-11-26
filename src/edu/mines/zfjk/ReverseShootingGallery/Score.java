/**
 * Description: Object to store and serialize information about a score set by a player during gameplay
 * @author Zach Fleischman
 */

package edu.mines.zfjk.ReverseShootingGallery;

public class Score {
    public String name;
    public int score;
    public String date;
    public boolean valid;

    public Score(String name, int score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
        this.valid = true;
    }

    public Score(String serializedScore) {
        String[] components = serializedScore.split(";");
        if (components.length == 3) {
            this.name = components[0];
            this.score = Integer.parseInt(components[1]);
            this.date = components[2];
            this.valid = true;
        } else {
            this.valid = false;
        }
    }

    /**
     * Not technically serialization, but normalizes the data into a form for storage
     *
     * @return A string of the form NNNNNNNNN;S;MM-DD-YY
     */
    public String serialize() {
        return name + ";" + score + ";" + date;
    }
}
