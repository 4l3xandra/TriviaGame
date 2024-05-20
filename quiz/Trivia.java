package quiz;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trivia {

    private List<Question> questions;

    public Trivia() {
        this.questions = new ArrayList<>();
    }

    // load questions from the OpenTDB API response
    public List<Question> loadQuestionsFromAPI(String apiResponse) {
        // parse the JSON response from the OpenTDB API
        // create question objects
        // add the question objects to the list of questions
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(apiResponse).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("results");

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject questionObject = jsonArray.get(i).getAsJsonObject();
            Question question = gson.fromJson(questionObject, Question.class);
            questions.add(question);
        }
        return null;
    }

    // select a random question
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions available");
        }
        Random random = new Random();
        return questions.get(random.nextInt(questions.size()));
    }

    // add a question to the list
    public void addQuestion(Question question) {
        questions.add(question);
    }

    // Method to remove a question from the list
    public void removeQuestion(Question question) {
        questions.remove(question);
    }
}