package quiz;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Scores {
    private Map<String, Integer> userScores; // Map of usernames to scores

    public Scores() {
        this.userScores = new HashMap<>();
    }

    // record a user's score
    public void recordScore(String username, int score) {
        userScores.put(username, score);
        logScore(username, score);
    }

    // log a user's score
    private void logScore(String username, int score) {
        try (FileWriter writer = new FileWriter("scores.log", true)) {
            writer.write(LocalDateTime.now() + ": " + username + " - Score: " + score + "\n");
        } catch (IOException e) {
            System.out.println("Failed to log score: " + e.getMessage());
        }
    }

    // get the number of quizzes taken by a user
    public int getQuizzesTaken(String username) {
        return userScores.containsKey(username) ? 1 : 0;
    }

    // calculate the average score of a user
    public double getAverageScore(String username) {
        int totalScore = userScores.getOrDefault(username, 0);
        int quizzesTaken = getQuizzesTaken(username);
        return quizzesTaken > 0 ? (double) totalScore / quizzesTaken : 0.0;
    }

    // get the number of quizzes taken by all users
    public int getTotalQuizzesTaken() {
        return userScores.size();
    }

    // calculate the average score of all users
    public double getOverallAverageScore() {
        if (userScores.isEmpty()) {
            return 0.0;
        }
        int totalScore = 0;
        for (int score : userScores.values()) {
            totalScore += score;
        }
        return (double) totalScore / userScores.size();
    }
}
