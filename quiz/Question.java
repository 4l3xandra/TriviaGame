package quiz;

import java.util.List;

public class Question {
    private String question;
    private List<String> incorrect_answers;
    private String correct_answer;

    public String getQuestionText() {
        return question;
    }

    public List<String> getIncorrectAnswers() {
        return incorrect_answers;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }
}