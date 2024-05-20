package quiz;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class QuizManager {
    private List<Observer> observers;
    private Trivia trivia;
    private static final String API_URL = "https://opentdb.com/api.php?amount=10";

    public QuizManager() {
        this.observers = new ArrayList<>();
        this.trivia = new Trivia();
    }

    // retrieve questions from the Open Trivia Database API
    public List<Question> retrieveQuestions() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                trivia.loadQuestionsFromAPI(response.toString());
            } else {
                throw new IOException("Failed to retrieve questions from API. Response code: " + responseCode);
            }
        } catch (MalformedURLException e) {
            logError("MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            logError("ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            logError("IOException: " + e.getMessage());
        }
        return null;
    }

    // log errors to error.log
    private void logError(String errorMessage) {
        try (FileWriter writer = new FileWriter("error.log", true)) {
            writer.write(LocalDateTime.now() + ": " + errorMessage + "\n");
        } catch (IOException e) {
            System.out.println("Failed to log error: " + e.getMessage());
        }
    }

    // add an observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // remove an observer
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // notify observers of an error
    private void notifyObservers(String errorMessage) {
        for (Observer observer : observers) {
            observer.update(null, errorMessage);
        }
    }

    public Trivia getTrivia() {
        return trivia;
    }
}
