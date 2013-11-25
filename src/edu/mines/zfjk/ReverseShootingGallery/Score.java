package edu.mines.zfjk.ReverseShootingGallery;

public class Score {
    public String name;
    public String score;
    public String date;
    public boolean valid;

    public Score(String name, int score, String date) {
        this.name = name;
        this.score = String.valueOf(score);
        this.date = date;
        this.valid = true;
    }

    public Score(String name, String score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
        this.valid = true;
    }

    public Score(String serializedScore) {
        String[] components = serializedScore.split(";");
        if (components.length == 3) {
            this.name = components[0];
            this.score = components[1];
            this.date = components[2];
            this.valid = true;
        } else {
            this.valid = false;
        }
    }

    public String serialize() {
        return name + ";" + score + ";" + date;
    }
}
