package tools.score;

public class Score {

    private static int score = 0;

    public static void plusScore(){
        ++score;
    }

    public static int getScore() {
        return score;
    }

    public static void scoreDown(){
        score = 0;
    }
}
