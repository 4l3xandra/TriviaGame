package quiz;

import java.io.*;

public class CSVScoreData {
    private static final String FILE_PATH = "scores.csv";

    public static void saveScore(String username, int score) {
        File file = new File(FILE_PATH);
        boolean fileExists = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (!fileExists) {
                writer.write("Username,Score");
                writer.newLine();
            }
            writer.write(username + "," + score);
            writer.newLine();
            System.out.println("Score saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isFirstLine = true; // skip the first line
            System.out.println("Scores:");
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip the first line
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String score = parts[1];
                    System.out.println(username + ": " + score);
                }
            }
            if (isFirstLine) {
                System.out.println("No scores available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}